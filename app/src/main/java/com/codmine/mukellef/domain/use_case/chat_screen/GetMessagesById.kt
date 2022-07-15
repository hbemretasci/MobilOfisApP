package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.messages.toMessage
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMessagesById @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, sender: String, receiver: String
    ): Flow<Resource<List<Message>>> = flow {
        try {
            emit(Resource.Loading())
            val messages = repository.getMessages(
                Constants.QUERY_NOTIFICATION, gib, vk, password, sender, receiver, Constants.NOTIFICATION_TYPE_MESSAGE
            ).messages.map { it.toMessage() }.reversed()
            emit(Resource.Success(messages))
        } catch(e: HttpException) {
            emit(Resource.Error((e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as UiText))
        } catch(e: IOException) {
            emit(Resource.Error(UiText.StringResources(R.string.internet_error)))
        }
    }
}