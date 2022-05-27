package com.codmine.mukellef.data.remote.dto.post_message

import com.codmine.mukellef.data.remote.dto.notifications.NotificationDto
import com.codmine.mukellef.data.remote.dto.notifications.UnreadNotificationDto
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