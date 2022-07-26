package com.codmine.mukellef.data.remote

import com.codmine.mukellef.data.remote.dto.balance.BalanceDto
import com.codmine.mukellef.data.remote.dto.documents.DocumentsDto
import com.codmine.mukellef.data.remote.dto.messages.MessagesDto
import com.codmine.mukellef.data.remote.dto.notifications.NotificationsDto
import com.codmine.mukellef.data.remote.dto.post_message.PostMessageDto
import com.codmine.mukellef.data.remote.dto.post_reading.ReadingNotificationDto
import com.codmine.mukellef.data.remote.dto.tax_payer.TaxPayerDto
import com.codmine.mukellef.data.remote.dto.unread.UnreadNotificationsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MobileOfficeApi {

    @GET("mobil_islem.asp")
    suspend fun getTaxPayer(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String
    ): TaxPayerDto

    @GET("mobil_islem.asp")
    suspend fun getNotifications(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Gonderenid") sender: String,
        @Query("Alanid") receiver: String,
        @Query("Bildirim_Tip") notificationType: String
    ): NotificationsDto

    @GET("mobil_islem.asp")
    suspend fun getDocuments(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Gonderenid") sender: String,
        @Query("Alanid") receiver: String,
        @Query("Bildirim_Tip") notificationType: String
    ): DocumentsDto

    @GET("mobil_islem.asp")
    suspend fun getMessages(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Gonderenid") sender: String,
        @Query("Alanid") receiver: String,
        @Query("Bildirim_Tip") notificationType: String
    ): MessagesDto

    @GET("mobil_islem.asp")
    suspend fun getUnreadNotifications(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("User_id") user: String,
        @Query("Tipi") type: String
    ): UnreadNotificationsDto

    @GET("mobil_islem.asp")
    suspend fun getBalance(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("User_id") user: String
    ): BalanceDto

    @GET("mobil_islem.asp")
    suspend fun postMessage(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Gonderenid") sender: String,
        @Query("Alanid") receiver: String,
        @Query("Tipi") type: String,
        @Query("Evrakid") documentId: String,
        @Query("BildirimMetni") messageContent: String,
        @Query("Bildirim_Tip") notificationType: String
    ): PostMessageDto

    @GET("mobil_islem.asp")
    suspend fun postReadingInfo(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Bildirimid") notificationId: String
    ): ReadingNotificationDto
}