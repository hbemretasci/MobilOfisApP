package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PostMessageDto(
    @SerializedName("bildirimgonderme")
    val postMessage: PostingDto,
    @SerializedName("bildirimler")
    val notifications: List<NotificationDto>,
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unReadNotifications: List<UnreadNotificationDto>
)