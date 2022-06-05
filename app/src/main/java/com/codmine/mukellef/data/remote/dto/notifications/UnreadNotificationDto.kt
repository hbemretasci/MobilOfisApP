package com.codmine.mukellef.data.remote.dto.notifications

import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.google.gson.annotations.SerializedName

data class UnreadNotificationDto(
    @SerializedName("Bildirim_Gonderen_User_id")
    val senderUserId: String,
    @SerializedName("Bildirim_Tip")
    val type: String,
    @SerializedName("Toplam")
    val total: String
)

fun UnreadNotificationDto.toUnreadNotification(): UnreadNotification {
    return UnreadNotification(
        senderUserId = senderUserId,
        unReadAmount = total
    )
}