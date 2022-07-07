package com.codmine.mukellef.presentation.login_screen

import com.codmine.mukellef.domain.model.tax_payer.TaxPayer
import com.codmine.mukellef.presentation.util.UiText

data class LoginScreenDataState(
    val isLoading: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val taxPayer: TaxPayer? = null
)
