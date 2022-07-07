package com.codmine.mukellef.presentation.balance_screen

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.runtime.internal.isLiveLiteralsEnabled
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.use_case.balance_screen.GetTransactions
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val getUserLoginData: GetUserLoginData
):ViewModel() {

    private val _dataState = mutableStateOf(BalanceScreenDataState())
    val dataState: MutableState<BalanceScreenDataState> = _dataState

    private val _appSettings = mutableStateOf(AppSettings())

    fun onEvent(event: BalanceEvent, context: Context) {
        when(event) {
            is BalanceEvent.LoadData -> {
                getAppSettings(context)
                getTransactionList()
            }
            is BalanceEvent.Refresh -> {
                getTransactionList()
            }
        }
    }

    private fun getTransactionList() {
        getTransactions(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = BalanceScreenDataState(
                        transactions = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _dataState.value = BalanceScreenDataState(
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error)
                    )
                }
                is Resource.Loading -> {
                    _dataState.value = BalanceScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}