package com.codmine.mukellef.domain.use_case.notification_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.post_reading.toReadingNotification
import com.codmine.mukellef.domain.model.notifications.ReadingNotification
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostNotificationReadingInfo @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, notificationId: String
    ): Flow<Resource<ReadingNotification>> = flow {
        /*
        try {
            emit(Resource.Loading())
            val notificationReadingInfo = repository.postReadingInfo(
                Constants.QUERY_READING, gib, vk, password, notificationId
            ).toReadingNotification()
            emit(Resource.Success(notificationReadingInfo))
        } catch(e: HttpException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        } catch(e: IOException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        }

         */
    }
}