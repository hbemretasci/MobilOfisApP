package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.notifications.toMessage
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Constants.NOTIFICATION_TYPE_MESSAGE
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.util.UiText
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostMessage @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    suspend operator fun invoke(
        gib: String, vk: String, password: String, sender: String, receiver: String, message: String): Resource<List<Message>> {
        return try {
            val messages = repository.postMessage(
                Constants.QUERY_NOTIFICATION, gib, vk, password, sender, receiver, NOTIFICATION_TYPE_MESSAGE,"0", message, NOTIFICATION_TYPE_MESSAGE
            ).notifications.map { it.toMessage() }.reversed()
            Resource.Success(messages)
        } catch(e: HttpException) {
            Resource.Error((e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as UiText)
        } catch(e: IOException) {
            Resource.Error(UiText.StringResources(R.string.internet_error))
        }
    }
}