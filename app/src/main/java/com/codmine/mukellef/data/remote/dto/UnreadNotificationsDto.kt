package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UnreadNotificationsDto(
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications : List<UnreadNotificationDto>
)

