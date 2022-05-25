package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MessagesDto(
    @SerializedName("bildirimler")
    val messages: List<MessageDto>,
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications: List<UnreadNotificationDto>
)
