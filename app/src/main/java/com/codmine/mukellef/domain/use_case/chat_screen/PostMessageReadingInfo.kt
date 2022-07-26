package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.post_reading.toReadingMessage
import com.codmine.mukellef.domain.model.chat.ReadingMessage
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostMessageReadingInfo @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    suspend operator fun invoke(
        gib: String, vk: String, password: String, messageId: String): Resource<ReadingMessage> {
        return try {
            val messageReadingInfo = repository.postReadingInfo(Constants.QUERY_READING, gib, vk, password, messageId).toReadingMessage()
            Resource.Success(messageReadingInfo)
        } catch(e: HttpException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            Resource.Error(message = UiText.DynamicString(errorMessage))
        } catch(e: IOException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            Resource.Error(message = UiText.DynamicString(errorMessage))
        }
    }
}