package com.codmine.mukellef.data.remote.dto.tax_payer

import com.google.gson.annotations.SerializedName

data class AccountantDto(
    @SerializedName("MM_User_Email")
    val eMail: String,
    @SerializedName("MM_User_Fax")
    val faxNumber: String,
    @SerializedName("MM_User_Gsm")
    val gsmNumber: String,
    @SerializedName("MM_User_Tel")
    val telNumber: String,
    @SerializedName("MM_User_Unvan")
    val title: String,
    @SerializedName("MM_User_id")
    val id: String
)