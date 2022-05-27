package com.codmine.mukellef.data.remote.dto.notifications

import com.google.gson.annotations.SerializedName

data class NotificationsDto(
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications: List<UnreadNotificationDto>,
    @SerializedName("bildirimler")
    val notifications: List<NotificationDto>
)