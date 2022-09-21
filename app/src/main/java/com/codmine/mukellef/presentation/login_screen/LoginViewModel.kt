package com.codmine.mukellef.presentation.login_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.login_screen.*
import com.codmine.mukellef.domain.use_case.main.LogOut
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
    private val setUserLoginData: SetUserLoginData,
    private val logIn: LogInWithEmailAndPassword,
    private val logOut: LogOut
): ViewModel() {
    var uiState by mutableStateOf(LoginScreenState())
        private set

    private val _uiEventChannel = Channel<LoginUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.GibChanged -> {
                uiState = uiState.copy(gib = event.gibValue)
            }
            is LoginEvent.VkChanged -> {
                uiState = uiState.copy(vk = event.vkValue)
            }
            is LoginEvent.PasswordChanged -> {
                uiState = uiState.copy(password = event.passwordValue)
            }
            is LoginEvent.Validate -> {
                validationData()
            }
            is LoginEvent.CheckLoginApi -> {
                checkLoginApi()
            }
            is LoginEvent.CheckLoginDatabase -> {
                checkLoginDatabase()
            }
        }
    }

    private fun validationData() {
        val gibResult = validateGib.execute(uiState.gib)
        val vkResult = validateVk.execute(uiState.vk)
        val passwordResult = validatePassword.execute(uiState.password)

        val hasError = listOf(
            gibResult,
            vkResult,
            passwordResult
        ).any { !it.successful }

        uiState = uiState.copy(
            gibError = gibResult.errorMessage,
            vkError = vkResult.errorMessage,
            passwordError = passwordResult.errorMessage
        )
        if(hasError) return
        viewModelScope.launch {
            _uiEventChannel.send(LoginUiEvent.ValidationSuccess)
        }
    }

    private fun checkLoginApi() {
        getTaxPayer(uiState.gib, uiState.vk, uiState.password).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        errorStatus = false,
                        taxPayer = null
                    )
                }
                is Resource.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message
                            ?: UiText.StringResources(R.string.unexpected_error),
                        taxPayer = null
                    )
                    _uiEventChannel.send(
                        LoginUiEvent.ShowSnackbar(
                            uiState.errorText ?: UiText.StringResources(R.string.unexpected_error)
                        )
                    )
                }
                is Resource.Success -> {
                    result.data?.let {
                        if (it.loginResult == RESULT_USER_LOGIN) {
                            uiState = uiState.copy(
                                isLoading = false,
                                errorStatus = false,
                                taxPayer = result.data
                            )
                            _uiEventChannel.send(LoginUiEvent.LoginSuccessApi)
                        } else {
                            uiState = uiState.copy(
                                isLoading = false,
                                errorStatus = true,
                                errorText = UiText.DynamicString(it.loginMessage),
                                taxPayer = null
                            )
                            _uiEventChannel.send(
                                LoginUiEvent.ShowSnackbar(
                                    uiState.errorText ?: UiText.StringResources(R.string.unexpected_error)
                                )
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkLoginDatabase() {
        val email: String = "UA" + uiState.vk + "@mobilise.com"
        logOut()
        logIn(email, uiState.password, ::logInSuccess) { error ->
            if (error != null) {
                uiState = uiState.copy(
                    isLoading = false,
                    errorStatus = true,
                    errorText = error.localizedMessage?.let { UiText.DynamicString(it) }
                )
                viewModelScope.launch {
                    _uiEventChannel.send(
                        LoginUiEvent.ShowSnackbar(
                            uiState.errorText ?: UiText.StringResources(R.string.unexpected_error)
                        )
                    )
                }
            }
        }
    }

    private fun logInSuccess() {
        viewModelScope.launch {
            setAppSettings(
                loginStatus = true,
                gib = uiState.gib,
                vk = uiState.vk,
                password = uiState.password,
                user = uiState.taxPayer?.userId ?: "",
                accountant = uiState.taxPayer?.accountantId ?: ""
            )
            _uiEventChannel.send(LoginUiEvent.LoginSuccessDatabase)
        }
    }

    private suspend fun setAppSettings(
        loginStatus: Boolean, gib: String, vk: String, password: String, user: String, accountant: String
    ) {
        setUserLoginData(loginStatus, gib, vk, password, user, accountant)
    }
}