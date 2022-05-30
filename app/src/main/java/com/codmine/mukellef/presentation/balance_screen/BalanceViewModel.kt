package com.codmine.mukellef.presentation.balance_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.use_case.balance_screen.GetTransactions
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val getUserLoginData: GetUserLoginData
):ViewModel() {

    private val _dataState = mutableStateOf(BalanceScreenDataState())
    val dataState: State<BalanceScreenDataState> = _dataState

    private val _appSettings = mutableStateOf(AppSettings())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun onEvent(event: BalanceEvent, context: Context) {
        when(event) {
            is BalanceEvent.LoadData -> {
                getAppSettings(context)
                getTransactionList()
            }
            is BalanceEvent.LoadTransactions -> {
                getTransactionList()
            }
            is BalanceEvent.Refresh -> {
                refresh()
            }
        }
    }

    private fun getTransactionList() {
        getTransactions(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = BalanceScreenDataState(transactions = result.data ?: emptyList() )
                }
                is Resource.Error -> {
                    _dataState.value = BalanceScreenDataState(error = result.message ?: "Beklenmeyen hata.")
                }
                is Resource.Loading -> {
                    _dataState.value = BalanceScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getTransactionList()
            _isRefreshing.emit(false)
        }
    }

    private fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}