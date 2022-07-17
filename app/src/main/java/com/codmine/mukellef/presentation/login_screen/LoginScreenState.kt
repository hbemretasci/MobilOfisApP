package com.codmine.mukellef.presentation.login_screen

import com.codmine.mukellef.domain.model.tax_payer.TaxPayer
import com.codmine.mukellef.domain.util.UiText

data class LoginScreenState(
    val isLoading: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val taxPayer: TaxPayer? = null,
    val gib: String = "",
    val gibError: UiText? = null,
    val vk: String = "",
    val vkError: UiText? = null,
    val password: String = "",
    val passwordError: UiText? = null
)
