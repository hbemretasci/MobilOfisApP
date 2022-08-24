package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMessagesById @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, sender: String, receiver: String
    ): Flow<Resource<List<PostMessage>>> = flow {
        /*
        try {
            emit(Resource.Loading())
            val messages = repository.getMessages(
                Constants.QUERY_NOTIFICATION, gib, vk, password, sender, receiver, Constants.NOTIFICATION_TYPE_MESSAGE
            ).messages.map { it.toMessage() }.reversed()
            emit(Resource.Success(messages))
        } catch(e: HttpException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        } catch(e: IOException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        }

         */
    }
}