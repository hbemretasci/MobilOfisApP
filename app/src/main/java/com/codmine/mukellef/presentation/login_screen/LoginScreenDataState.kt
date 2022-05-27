package com.codmine.mukellef.presentation.login_screen

import com.codmine.mukellef.domain.model.tax_payer.TaxPayer

data class LoginScreenDataState(
    val isLoading: Boolean = false,
    val taxPayer: TaxPayer? = null,
    val error: String = ""
)
