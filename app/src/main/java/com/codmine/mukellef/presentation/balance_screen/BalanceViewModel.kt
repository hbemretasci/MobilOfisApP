package com.codmine.mukellef.presentation.balance_screen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.balance_screen.GetTransactions
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(
    private val getTransactions: GetTransactions,
    private val getUserLoginData: GetUserLoginData
):ViewModel() {
    var uiState by mutableStateOf(BalanceScreenDataState())
        private set

    private val _appSettings = mutableStateOf(AppSettings())

    fun onEvent(event: BalanceEvent) {
        when(event) {
            is BalanceEvent.LoadData -> {
                getAppSettings()
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
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = false,
                        transactions = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        transactions = emptyList()
                    )
                }
                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        errorStatus = false,
                        transactions = emptyList()
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}