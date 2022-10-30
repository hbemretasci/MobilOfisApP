package com.codmine.mukellef.presentation.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.login_screen.*
import com.codmine.mukellef.domain.use_case.main.LogOut
import com.codmine.mukellef.domain.util.Constants.RESULT_USER_LOGIN
import com.codmine.mukellef.domain.util.Constants.USER_PREFIX
import com.codmine.mukellef.domain.util.Constants.USER_SUFFIX
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
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
    private val logOut: LogOut,
    private val setOnesignalExternalId: SetOnesignalExternalId,
    private val getOnesignalPlayerId: GetOnesignalPlayerId,
    private val addOrUpdateUser: AddOrUpdateUser
): ViewModel() {
    private val _uiState = MutableStateFlow(LoginScreenState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<LoginUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.GibChanged -> {
                _uiState.value = uiState.value.copy(gib = event.gibValue)
            }
            is LoginEvent.VkChanged -> {
                _uiState.value = uiState.value.copy(vk = event.vkValue)
            }
            is LoginEvent.PasswordChanged -> {
                _uiState.value = uiState.value.copy(password = event.passwordValue)
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
        val gibResult = validateGib.execute(uiState.value.gib)
        val vkResult = validateVk.execute(uiState.value.vk)
        val passwordResult = validatePassword.execute(uiState.value.password)

        val hasError = listOf(
            gibResult,
            vkResult,
            passwordResult
        ).any { !it.successful }

        _uiState.value = uiState.value.copy(
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
        getTaxPayer(uiState.value.gib, uiState.value.vk, uiState.value.password).onEach { result ->
            when (result) {
                is Resource.Loading -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = true,
                        errorStatus = false,
                        taxPayer = null
                    )
                }
                is Resource.Error -> {
                    _uiState.value = uiState.value.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        taxPayer = null
                    )
                    _uiEventChannel.send(
                        LoginUiEvent.ShowSnackbar(
                            uiState.value.errorText ?: UiText.StringResources(R.string.unexpected_error)
                        )
                    )
                }
                is Resource.Success -> {
                    result.data?.let {
                        if (it.loginResult == RESULT_USER_LOGIN) {
                            _uiState.value = uiState.value.copy(
                                errorStatus = false,
                                taxPayer = result.data
                            )
                            _uiEventChannel.send(LoginUiEvent.LoginSuccessApi)
                        } else {
                            _uiState.value = uiState.value.copy(
                                isLoading = false,
                                errorStatus = true,
                                errorText = UiText.DynamicString(it.loginMessage),
                                taxPayer = null
                            )
                            _uiEventChannel.send(
                                LoginUiEvent.ShowSnackbar(
                                    uiState.value.errorText ?: UiText.StringResources(R.string.unexpected_error)
                                )
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun checkLoginDatabase() {
        val email: String = USER_PREFIX + uiState.value.vk + USER_SUFFIX
        logOut()
        logIn(email, uiState.value.password, ::logInSuccess) { error ->
            if (error != null) {
                _uiState.value = uiState.value.copy(
                    isLoading = false,
                    errorStatus = true,
                    errorText = error.localizedMessage?.let { UiText.DynamicString(it) }
                )
                viewModelScope.launch {
                    _uiEventChannel.send(
                        LoginUiEvent.ShowSnackbar(
                            uiState.value.errorText ?: UiText.StringResources(R.string.unexpected_error)
                        )
                    )
                }
            }
        }
    }

    private fun logInSuccess() {
        _uiState.value = uiState.value.copy(isLoading = false)

        viewModelScope.launch {
            setAppSettings(
                loginStatus = true,
                gib = uiState.value.gib,
                vk = uiState.value.vk,
                password = uiState.value.password,
                user = uiState.value.taxPayer?.userId ?: "",
                accountant = uiState.value.taxPayer?.accountantId ?: ""
            )
            _uiEventChannel.send(LoginUiEvent.LoginSuccessDatabase)
        }

        setOnesignalExternalId(USER_PREFIX + uiState.value.vk)
        val playerId = getOnesignalPlayerId()
        uiState.value.taxPayer?.userId?.let { addOrUpdateUser(it, playerId) }
    }

    private suspend fun setAppSettings(
        loginStatus: Boolean, gib: String, vk: String, password: String, user: String, accountant: String
    ) {
        setUserLoginData(loginStatus, gib, vk, password, user, accountant)
    }
}