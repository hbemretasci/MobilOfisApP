package com.codmine.mukellef.presentation.login_screen

sealed class LoginUiEvent {
    data class ShowSnackbar(val message: String): LoginUiEvent()
    object ValidationSuccess: LoginUiEvent()
    object Login: LoginUiEvent()
}
