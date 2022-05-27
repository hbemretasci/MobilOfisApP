package com.codmine.mukellef.data.repository

import com.codmine.mukellef.data.remote.MobileOfficeApi
import com.codmine.mukellef.data.remote.dto.balance.BalanceDto
import com.codmine.mukellef.data.remote.dto.documents.DocumentsDto
import com.codmine.mukellef.data.remote.dto.messages.MessagesDto
import com.codmine.mukellef.data.remote.dto.notifications.NotificationsDto
import com.codmine.mukellef.data.remote.dto.post_message.PostMessageDto
import com.codmine.mukellef.data.remote.dto.post_reading.ReadingNotificationDto
import com.codmine.mukellef.data.remote.dto.tax_payer.TaxPayerDto
import com.codmine.mukellef.data.remote.dto.unread.UnreadNotificationsDto
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import javax.inject.Inject

class MobileOfficeRepositoryImpl @Inject constructor(
    private val api: MobileOfficeApi
): MobileOfficeRepository {

    override suspend fun getNotifications(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        notificationType: String
    ): NotificationsDto {
        return api.getNotifications(
            queryType, gib, vk, password, sender, receiver, notificationType
        )
    }

    override suspend fun getDocuments(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        notificationType: String
    ): DocumentsDto {
        return api.getDocuments(
            queryType, gib, vk, password, sender, receiver, notificationType
        )
    }

    override suspend fun getMessages(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        notificationType: String
    ): MessagesDto {
        return api.getMessages(
            queryType, gib, vk, password, sender, receiver, notificationType
        )
    }

    override suspend fun getUnreadNotifications(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        user: String,
        type: String
    ): UnreadNotificationsDto {
        return api.getUnreadNotifications(
            queryType, gib, vk, password, user, type
        )
    }

    override suspend fun getTaxPayer(
        queryType: String,
        gib: String,
        vk: String,
        password: String
    ): TaxPayerDto {
        return api.getTaxPayer(
            queryType, gib, vk, password
        )
    }

    override suspend fun getBalance(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        user: String
    ): BalanceDto {
        return api.getBalance(
            queryType, gib, vk, password, user
        )
    }

    override suspend fun postMessage(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        sender: String,
        receiver: String,
        type: String,
        documentId: String,
        messageContent: String,
        notificationType: String
    ): PostMessageDto {
        return api.postMessage(
            queryType, gib, vk, password, sender, receiver,
            type, documentId, messageContent, notificationType
        )
    }

    override suspend fun postReadingInfo(
        queryType: String,
        gib: String,
        vk: String,
        password: String,
        notificationId: String
    ): ReadingNotificationDto {
        return api.postReadingInfo(
            queryType, gib, vk, password, notificationId
        )
    }
}