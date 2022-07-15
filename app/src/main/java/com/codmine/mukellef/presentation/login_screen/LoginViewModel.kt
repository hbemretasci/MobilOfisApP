package com.codmine.mukellef.presentation.login_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.login_screen.*
import com.codmine.mukellef.domain.util.Constants.RESULT_USER_LOGIN
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validateGib: ValidateGib,
    private val validateVk: ValidateVk,
    private val validatePassword: ValidatePassword,
    private val getTaxPayer: GetTaxPayer,
    private val setUserLoginData: SetUserLoginData
): ViewModel() {
    
    private val _dataState = mutableStateOf(LoginScreenDataState())
    val dataState: State<LoginScreenDataState> = _dataState

    private val _viewState = mutableStateOf(LoginScreenViewState())
    val viewState: State<LoginScreenViewState> = _viewState

    private val _uiEventChannel = Channel<LoginUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.GibChanged -> {
                _viewState.value = _viewState.value.copy(gib = event.gibValue)
            }
            is LoginEvent.VkChanged -> {
                _viewState.value = _viewState.value.copy(vk = event.vkValue)
            }
            is LoginEvent.PasswordChanged -> {
                _viewState.value = _viewState.value.copy(password = event.passwordValue)
            }
            is LoginEvent.Validate -> {
                validationData()
            }
            is LoginEvent.CheckLogin -> {
                checkLogin()
            }
        }
    }

    private fun validationData() {
        val gibResult = validateGib.execute(_viewState.value.gib)
        val vkResult = validateVk.execute(_viewState.value.vk)
        val passwordResult = validatePassword.execute(_viewState.value.password)

        val hasError = listOf(
            gibResult,
            vkResult,
            passwordResult
        ).any { !it.successful }

        _viewState.value = _viewState.value.copy(
            gibError = gibResult.errorMessage,
            vkError = vkResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )
        if(hasError) return
        viewModelScope.launch {
            _uiEventChannel.send(LoginUiEvent.ValidationSuccess)
        }
    }

    private fun checkLogin() {
        getTaxPayer(
            _viewState.value.gib, _viewState.value.vk, _viewState.value.password
        ).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _dataState.value = LoginScreenDataState(taxPayer = result.data)
                    _dataState.value.taxPayer?.let {
                        if(it.loginResult == RESULT_USER_LOGIN) {
                            setAppSettings(
                                true,
                                _viewState.value.gib,
                                _viewState.value.vk,
                                _viewState.value.password,
                                it.userId?: "",
                                it.accountantId?: ""
                            )
                            _uiEventChannel.send(LoginUiEvent.Login)
                        } else {
                            _uiEventChannel.send(
                                LoginUiEvent.ShowSnackbar(UiText.DynamicString(it.loginMessage))
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _dataState.value = LoginScreenDataState(
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error)
                    )
                    _uiEventChannel.send(
                        LoginUiEvent.ShowSnackbar(_dataState.value.errorText ?: UiText.StringResources(R.string.unexpected_error))
                    )
                }
                is Resource.Loading -> {
                    _dataState.value = LoginScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun setAppSettings(loginStatus: Boolean, gib: String, vk: String, password: String,
        user: String, accountant: String
    ) {
        setUserLoginData(loginStatus, gib, vk, password, user, accountant)
    }
}