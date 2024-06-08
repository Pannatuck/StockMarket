package dev.pan.stockmarket.data.repository

import coil.network.HttpException
import dev.pan.stockmarket.data.local.StockDatabase
import dev.pan.stockmarket.data.mapper.toCompanyListing
import dev.pan.stockmarket.data.remote.StockAPI
import dev.pan.stockmarket.domain.model.CompanyListing
import dev.pan.stockmarket.domain.repository.StockRepository
import dev.pan.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOError
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockAPI,
    val db: StockDatabase
) : StockRepository {

    private val dao = db.dao

    /* Implementation of domain layer repository interface.
    * Through flow we emit loading first to indicate that process begun and getting data from cache.
    * We get data from DB and map it to domain model.
    * Than we check if DB is empty, if it's not we dont fetch data from remote.
    * Also we create value that will be used to fetching data from remote later, when user will pull refresh page*/
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        // get data from cache
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map {
                    it.toCompanyListing()
                }
            ))

            // check if db empty
            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            // fetch from remote
            val remoteListing = try {
                val response = api.getListings()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }
}