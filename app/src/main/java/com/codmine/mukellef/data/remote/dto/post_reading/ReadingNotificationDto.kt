package com.codmine.mukellef.data.remote.dto.post_reading

import com.codmine.mukellef.domain.model.documents.ReadingDocument
import com.google.gson.annotations.SerializedName

data class ReadingNotificationDto(
    @SerializedName("bildirimokuma")
    val reading: ReadingDto,
    @SerializedName("islemzamani")
    val processingTime: String
)

fun ReadingNotificationDto.toReadingDocument(): ReadingDocument {
    return ReadingDocument(
        processingTime = processingTime,
        readingMessage = reading.readingMessage,
        readingResult = reading.readingResult
    )
}