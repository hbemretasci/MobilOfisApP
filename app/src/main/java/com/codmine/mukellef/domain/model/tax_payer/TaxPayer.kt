package com.codmine.mukellef.domain.model.tax_payer

data class TaxPayer(
    val loginResult: String,
    val loginMessage: String,
    val userId: String? = "",
    val accountantId: String? = ""
)
