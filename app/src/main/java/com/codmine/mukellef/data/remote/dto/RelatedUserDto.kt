package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class RelatedUserDto(
    @SerializedName("Bagli_Ad")
    val name: String,
    @SerializedName("Bagli_Durum")
    val status: String,
    @SerializedName("Bagli_Email")
    val eMail: String,
    @SerializedName("Bagli_Fax")
    var faxNumber: String,
    @SerializedName("Bagli_Gsm")
    val gsmNumber: String,
    @SerializedName("Bagli_Pass")
    val password: String,
    @SerializedName("Bagli_Tc")
    val idNumber: String,
    @SerializedName("Bagli_Tel")
    val telNumber: String,
    @SerializedName("Bagli_Tip")
    val type: String,
    @SerializedName("Bagli_Vk")
    val vkNumber: String,
    @SerializedName("Bagli_id")
    val id: String,
    @SerializedName("Bagli_Bos")
    val unReadCount: String
)