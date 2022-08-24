package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import javax.inject.Inject

class PostMessageReadingInfo @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    suspend operator fun invoke(
        gib: String, vk: String, password: String, messageId: String) {
        /*
        return try {
            val messageReadingInfo = repository.postReadingInfo(Constants.QUERY_READING, gib, vk, password, messageId).toReadingMessage()
            Resource.Success(messageReadingInfo)
        } catch(e: HttpException) {
            Resource.Error(message = UiText.StringResources(R.string.server_connection_error))
        } catch(e: IOException) {
            Resource.Error(message = UiText.StringResources(R.string.server_connection_error))
        }

         */
    }
}