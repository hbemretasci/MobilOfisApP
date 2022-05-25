package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UnreadNotificationDto(
    @SerializedName("Bildirim_Gonderen_User_id")
    val senderUserId: String,
    @SerializedName("Bildirim_Tip")
    val type: String,
    @SerializedName("Toplam")
    val total: String
)