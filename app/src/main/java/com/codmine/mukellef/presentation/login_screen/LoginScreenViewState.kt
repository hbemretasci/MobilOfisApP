package com.codmine.mukellef.presentation.login_screen

data class LoginScreenViewState(
    val gib: String = "",
    val gibError: String? = null,
    val vk: String = "",
    val vkError: String? = null,
    val password: String = "",
    val passwordError: String? = null
)
