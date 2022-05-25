package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DocumentsDto(
    @SerializedName("bildirimler")
    val documents: List<DocumentDto>,
    @SerializedName("islemzamani")
    val processingTime: String,
    @SerializedName("okunmamis_bildirimler")
    val unreadNotifications: List<UnreadNotificationDto>
)
