package com.codmine.mukellef.data.remote.dto.messages

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("Bildirim_Evrak_Varmi")
    val documentName: String,
    @SerializedName("Bildirim_Alan_User_id")
    val receivedUserId: String,
    @SerializedName("Bildirim_Atim_Zaman")
    val postTime: String,
    @SerializedName("Bildirim_Gonderen_User_Unvan")
    val senderUser: String,
    @SerializedName("Bildirim_Gonderen_User_id")
    val senderUserId: String,
    @SerializedName("Bildirim_Metin")
    val message: String,
    @SerializedName("Bildirim_Okuma_Zaman")
    var readingTime: String,
    @SerializedName("Bildirim_Tip")
    val type: String,
    @SerializedName("Bildirim_id")
    val id: String
)
