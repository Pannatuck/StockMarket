package dev.pan.stockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object{
        const val API_KEY = "TB1YU3SEL8UCZIKE"
        const val BASE_URL = "https://www.alphavantage.co"
    }
}