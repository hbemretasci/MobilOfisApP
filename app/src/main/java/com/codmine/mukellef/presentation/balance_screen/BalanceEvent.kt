package com.codmine.mukellef.presentation.balance_screen

sealed class BalanceEvent{
    object LoadData: BalanceEvent()
    object LoadTransactions: BalanceEvent()
    object Refresh: BalanceEvent()
}
