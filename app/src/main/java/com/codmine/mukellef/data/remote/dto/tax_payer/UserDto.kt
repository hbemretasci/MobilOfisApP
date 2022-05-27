package com.codmine.mukellef.data.remote.dto.tax_payer

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("MM_User_id")
    val id: String,
    @SerializedName("User_Email")
    val eMail: String,
    @SerializedName("User_Fax")
    val faxNumber: String,
    @SerializedName("User_Gibno")
    val gibNumber: String,
    @SerializedName("User_Gibparola")
    val gibPass: String,
    @SerializedName("User_Gibsifre")
    val gibPassword: String,
    @SerializedName("User_Gsm")
    val gsmNumber: String,
    @SerializedName("User_Tc")
    val idNumber: String,
    @SerializedName("User_Tel")
    val telNumber: String,
    @SerializedName("User_Tip")
    val type: String,
    @SerializedName("User_Unvan")
    val title: String,
    @SerializedName("User_Vk")
    val vkNumber: String,
    @SerializedName("baglikullanicilar")
    val relatedUsers: List<RelatedUserDto>
)