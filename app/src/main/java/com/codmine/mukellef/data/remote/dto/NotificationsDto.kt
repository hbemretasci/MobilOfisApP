package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class NotificationsDto(
    @SerializedName("bildirimler")
    val notifications: List<NotificationDto>,
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications: List<UnreadNotificationDto>
)