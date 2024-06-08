package dev.pan.stockmarket.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {
    // to save data about companies in DB
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    // delete all data from DB
    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    // search for companies in DB
    /* in WHERE block we transform search string that user typed to lower case and compare it to name of the company like tes == %tes%
    * or if user typed name in upper case, just searching for it to match the name of the company. || in SQLite is concatination operator (+ in Kotlin strings)*/
    @Query(
        """
            SELECT *
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>

}