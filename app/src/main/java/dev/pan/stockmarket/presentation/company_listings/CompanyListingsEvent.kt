package dev.pan.stockmarket.presentation.company_listings

import dev.pan.stockmarket.data.local.CompanyListingEntity

sealed class CompanyListingsEvent {
    object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()

}