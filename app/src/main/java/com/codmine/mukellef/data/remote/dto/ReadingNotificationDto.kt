package com.codmine.mukellef.data.remote.dto

import com.codmine.mukellef.data.remote.dto.ReadingDto
import com.google.gson.annotations.SerializedName

data class ReadingNotificationDto(
    @SerializedName("bildirimokuma")
    val reading: ReadingDto,
    @SerializedName("islemzamani")
    val processingTime: String
)