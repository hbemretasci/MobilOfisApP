package com.codmine.mukellef.domain.repository

import com.codmine.mukellef.data.remote.dto.balance.BalanceDto
import com.codmine.mukellef.data.remote.dto.documents.DocumentsDto
import com.codmine.mukellef.data.remote.dto.notifications.NotificationsDto
import com.codmine.mukellef.data.remote.dto.post_reading.ReadingNotificationDto
import com.codmine.mukellef.data.remote.dto.tax_payer.TaxPayerDto

interface MobileOfficeRepository {

    suspend fun getNotifications(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        notificationType: String
    ): NotificationsDto

    suspend fun getDocuments(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        notificationType: String
    ): DocumentsDto

    suspend fun getTaxPayer(
        queryType: String,
        gib: String,
        vk: String,
        password: String
    ): TaxPayerDto

    suspend fun getBalance(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        user: String
    ): BalanceDto

    suspend fun postReadingInfo(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        notificationId: String
    ): ReadingNotificationDto

}