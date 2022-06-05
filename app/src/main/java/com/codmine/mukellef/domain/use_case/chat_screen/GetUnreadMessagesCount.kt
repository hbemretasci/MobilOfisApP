package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.data.remote.dto.notifications.toUnreadNotification
import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Constants.NOTIFICATION_TYPE_MESSAGE
import com.codmine.mukellef.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUnreadMessagesCount @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    suspend operator fun invoke(gib: String, vk: String, password: String, user: String
    ): Resource<List<UnreadNotification>> {
        return try {
            val unreadData = repository.getUnreadNotifications(
                Constants.QUERY_UNREAD, gib, vk, password, user, NOTIFICATION_TYPE_MESSAGE
            ).unreadNotifications.map { it.toUnreadNotification() }
            Resource.Success(unreadData ?: emptyList())
        } catch(e: HttpException) {
            Resource.Error(e.localizedMessage ?: "Beklenmeyen hata.")
        } catch(e: IOException) {
            Resource.Error("Sunucuya erişilemiyor, bağlantı hatası.")
        }
    }
}