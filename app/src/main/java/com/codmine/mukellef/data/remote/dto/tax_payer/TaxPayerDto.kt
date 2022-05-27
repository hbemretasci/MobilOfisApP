package com.codmine.mukellef.data.remote.dto.tax_payer

import com.codmine.mukellef.domain.model.tax_payer.TaxPayer
import com.google.gson.annotations.SerializedName

data class TaxPayerDto(
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("kullanici")
    val user: UserDto?,
    @SerializedName("kullanicidogrulama")
    val userAuthentication: UserAuthenticationDto,
    @SerializedName("malimusavir")
    val accountant: AccountantDto?
)

fun TaxPayerDto.toTaxPayer(): TaxPayer {
    return TaxPayer(
        loginResult = userAuthentication.loginResult,
        loginMessage = userAuthentication.loginMessage,
        userId = user?.let {
            it.id
        },
        accountantId = accountant?.let {
            it.id
        }
    )
}