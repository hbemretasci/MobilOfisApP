package com.codmine.mukellef.data.remote.dto.messages

import com.codmine.mukellef.data.remote.dto.notifications.UnreadNotificationDto
import com.google.gson.annotations.SerializedName

data class MessagesDto(
    @SerializedName("bildirimler")
    val messages: List<MessageDto>,
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications: List<UnreadNotificationDto>
)
