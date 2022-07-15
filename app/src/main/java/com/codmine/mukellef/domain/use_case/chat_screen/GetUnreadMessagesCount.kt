package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.notifications.toUnreadNotification
import com.codmine.mukellef.domain.model.chat.UnreadNotification
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Constants.NOTIFICATION_TYPE_MESSAGE
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
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
            Resource.Success(unreadData)
        } catch(e: HttpException) {
            Resource.Error((e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as UiText)
        } catch(e: IOException) {
            Resource.Error(UiText.StringResources(R.string.internet_error))
        }
    }
}