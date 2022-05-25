package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserAuthenticationDto(
    @SerializedName("GirisSonucu")
    val loginResult: String,
    @SerializedName("Msg")
    val loginMessage: String
)