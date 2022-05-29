package com.codmine.mukellef.presentation.balance_screen

import com.codmine.mukellef.domain.model.balance.Transaction

data class BalanceScreenDataState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList(),
    val error: String = ""
)
