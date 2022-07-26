package com.codmine.mukellef.domain.use_case.notification_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.notifications.toNotification
import com.codmine.mukellef.domain.model.notifications.Notification
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants.NOTIFICATION_TYPE_NOTIFICATION
import com.codmine.mukellef.domain.util.Constants.QUERY_NOTIFICATION
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetNotifications @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, sender: String, receiver: String
    ): Flow<Resource<List<Notification>>> = flow {
        try {
            emit(Resource.Loading())
            val notifications = repository.getNotifications(
                QUERY_NOTIFICATION, gib, vk, password, sender, receiver, NOTIFICATION_TYPE_NOTIFICATION
            ).notifications.map { it.toNotification() }
            emit(Resource.Success(notifications))
        } catch(e: HttpException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            emit(Resource.Error(message = UiText.DynamicString(errorMessage)))
        } catch(e: IOException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            emit(Resource.Error(message = UiText.DynamicString(errorMessage)))
        }
    }
}