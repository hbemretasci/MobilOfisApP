package com.codmine.mukellef.presentation.login_screen

import com.codmine.mukellef.presentation.util.UiText

data class LoginScreenViewState(
    val gib: String = "",
    val gibError: UiText? = null,
    val vk: String = "",
    val vkError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null
)
