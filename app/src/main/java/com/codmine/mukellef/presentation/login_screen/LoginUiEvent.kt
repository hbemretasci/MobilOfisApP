package com.codmine.mukellef.presentation.login_screen

import com.codmine.mukellef.domain.util.UiText

sealed class LoginUiEvent {
    data class ShowSnackbar(val message: UiText): LoginUiEvent()
    object ValidationSuccess: LoginUiEvent()
    object Login: LoginUiEvent()
}
