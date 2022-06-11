package com.codmine.mukellef.data.remote.dto.post_reading

import com.codmine.mukellef.domain.model.chat.ReadingMessage
import com.codmine.mukellef.domain.model.documents.ReadingDocument
import com.codmine.mukellef.domain.model.notifications.ReadingNotification
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

fun ReadingNotificationDto.toReadingNotification(): ReadingNotification {
    return ReadingNotification(
        processingTime = processingTime,
        readingMessage = reading.readingMessage,
        readingResult = reading.readingResult
    )
}

fun ReadingNotificationDto.toReadingMessage(): ReadingMessage {
    return ReadingMessage(
        processingTime = processingTime,
        readingMessage = reading.readingMessage,
        readingResult = reading.readingResult
    )
}