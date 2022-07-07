package com.codmine.mukellef.presentation.balance_screen

import com.codmine.mukellef.domain.model.balance.Transaction
import com.codmine.mukellef.presentation.util.UiText

data class BalanceScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val transactions: List<Transaction> = emptyList()
)