package com.codmine.mukellef.data.remote.dto.balance

import com.codmine.mukellef.domain.model.balance.Transaction
import com.google.gson.annotations.SerializedName

data class TransactionDto(
    @SerializedName("Bildirim_Evrak_Varmi")
    val documentName: String,
    @SerializedName("Bildirim_Alan_User_id")
    val receivedUserId: String,
    @SerializedName("Bildirim_Atim_Zaman")
    val postTime: String,
    @SerializedName("Bildirim_Gonderen_User_Unvan")
    val senderUser: String,
    @SerializedName("Bildirim_Gonderen_User_id")
    val senderUserId: String,
    @SerializedName("Bildirim_Metin")
    val message: String,
    @SerializedName("Bildirim_Okuma_Zaman")
    var readingTime: String,
    @SerializedName("Bildirim_Tip")
    val type: String,
    @SerializedName("Bildirim_id")
    val id: String
)

fun TransactionDto.toTransaction(): Transaction {
    val before = message.substringBeforeLast("-")
    val after = message.substringAfterLast("-")
    return Transaction(
        dateTime = before,
        name = after.substringBeforeLast("|"),
        amount = after.substringAfterLast("|")
    )
}