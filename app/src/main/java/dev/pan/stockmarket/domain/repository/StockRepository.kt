package dev.pan.stockmarket.domain.repository

import dev.pan.stockmarket.domain.model.CompanyListing
import dev.pan.stockmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    /*Flow is used, because several operations can go with this function. We can get data from cache,
    * which will return Resource.Success and we can fetch data from remote which can return other Result*/
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> // domain layer should return domain model data, not entities from local layer

}