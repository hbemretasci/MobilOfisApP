package com.codmine.mukellef.presentation.chat_screen.person

import com.codmine.mukellef.domain.model.tax_payer.RelatedUser

data class PersonScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val relatedUsers: List<RelatedUser>? = emptyList(),
    val error: String? = null
)
