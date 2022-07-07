package com.codmine.mukellef.presentation.chat_screen.person

import com.codmine.mukellef.domain.model.tax_payer.RelatedUser
import com.codmine.mukellef.presentation.util.UiText

data class PersonScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val relatedUsers: List<RelatedUser> = emptyList()
)
