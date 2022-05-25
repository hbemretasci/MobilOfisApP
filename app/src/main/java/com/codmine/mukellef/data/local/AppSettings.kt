package com.codmine.mukellef.data.local

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val loginData: Boolean = false,
    val gibData: String = "",
    val vkData: String = "",
    val passwordData: String = "",
    val userData: String = "",
    val accountantData: String = ""
)