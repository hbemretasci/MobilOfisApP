package com.codmine.mukellef.data.remote.dto

import com.codmine.mukellef.domain.model.TaxPayer
import com.google.gson.annotations.SerializedName
import okhttp3.internal.userAgent

data class TaxPayerDto(
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("kullanici")
    val user: UserDto,
    @SerializedName("kullanicidogrulama")
    val userAuthentication: UserAuthenticationDto,
    @SerializedName("malimusavir")
    val accountant: AccountantDto
)

fun TaxPayerDto.toTaxPayer(): TaxPayer {
    return TaxPayer(
        loginResult = userAuthentication.loginResult,
        loginMessage = userAuthentication.loginMessage,
        userId = user.id,
        accountantId = accountant.id
    )
}