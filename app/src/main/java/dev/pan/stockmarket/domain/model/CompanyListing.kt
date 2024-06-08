package dev.pan.stockmarket.domain.model


/* Same parameters as in DB. Middle layer between data and view models.
Can be useful if there will be need in changing DB to Realm for example.
Parameters and functions will be the same for domain and view layer models */
data class CompanyListing(
    val symbol: String,
    val name: String,
    val exchange: String,
)
