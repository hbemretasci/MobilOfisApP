package com.codmine.mukellef.data.repository

import com.codmine.mukellef.data.remote.MobileOfficeApi
import com.codmine.mukellef.data.remote.dto.balance.BalanceDto
import com.codmine.mukellef.data.remote.dto.documents.DocumentsDto
import com.codmine.mukellef.data.remote.dto.notifications.NotificationsDto
import com.codmine.mukellef.data.remote.dto.tax_payer.TaxPayerDto
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
}