package com.codmine.mukellef.presentation.login_screen

sealed class LoginEvent {
    data class GibChanged(val gibValue: String): LoginEvent()
    data class VkChanged(val vkValue: String): LoginEvent()
    data class PasswordChanged(val passwordValue: String): LoginEvent()
    object Validate: LoginEvent()
    object CheckLoginApi: LoginEvent()
    object CheckLoginDatabase: LoginEvent()
}
