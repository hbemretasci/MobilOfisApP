package com.codmine.mukellef.data.remote

import com.codmine.mukellef.data.remote.dto.*
import retrofit2.http.GET
import retrofit2.http.Query

interface MobileOfficeApi {

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=5&GibNo=95600494&User_Vk=2111196061&User_Pass=456&Gonderenid=2&Alanid=1&Bildirim_Tip=1
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

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=5&GibNo=95600494&User_Vk=2111196061&User_Pass=456&Gonderenid=2&Alanid=1&Bildirim_Tip=1
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

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=5&GibNo=95600494&User_Vk=2111196061&User_Pass=456&Gonderenid=2&Alanid=1&Bildirim_Tip=1
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

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=4&GibNo=95600494&User_Vk=2111196061&User_Pass=456&User_id=2&&Tipi=2
    @GET("mobil_islem.asp")
    suspend fun getUnreadNotifications(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("User_id") user: String,
        @Query("Tipi") type: String
    ): UnreadNotificationsDto

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=1&GibNo=95600494&User_Vk=2111196061&User_Pass=456
    @GET("mobil_islem.asp")
    suspend fun getTaxPayer(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String
    ): TaxPayerDto

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=9&GibNo=95600494&User_Vk=2111196061&User_Pass=456&User_id=2
    @GET("mobil_islem.asp")
    suspend fun getBalance(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("User_id") user: String
    ): BalanceDto

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=5&GibNo=95600494&User_Vk=2111196061&User_Pass=456&Gonderenid=2&Alanid=3&Tipi=2&Evrakid=0&BildirimMetni=son%20mesaj&Bildirim_Tip=2
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

    //http://www.ipeksu.com/mobil/mobil_islem.asp?CodMineMobil=3&GibNo=95600494&User_Vk=9560052737&User_Pass=123&Bildirimid=1
    @GET("mobil_islem.asp")
    suspend fun postReadingInfo(
        @Query("CodMineMobil") queryType: String,
        @Query("GibNo") gib: String,
        @Query("User_Vk") vk: String,
        @Query("User_Pass") password: String,
        @Query("Bildirimid") notificationId: String
    ): ReadingNotificationDto
}