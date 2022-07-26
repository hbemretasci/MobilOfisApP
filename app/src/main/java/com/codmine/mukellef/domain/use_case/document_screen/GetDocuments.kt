package com.codmine.mukellef.domain.use_case.document_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.documents.toDocument
import com.codmine.mukellef.domain.model.documents.Document
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Constants.NOTIFICATION_TYPE_DOCUMENT
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetDocuments @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, sender: String, receiver: String
    ): Flow<Resource<List<Document>>> = flow {
        try {
            emit(Resource.Loading())
            val documents = repository.getDocuments(
                Constants.QUERY_NOTIFICATION, gib, vk, password, sender, receiver, NOTIFICATION_TYPE_DOCUMENT
            ).documents.map { it.toDocument() }
            emit(Resource.Success(documents))
        } catch(e: HttpException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            emit(Resource.Error(message = UiText.DynamicString(errorMessage)))
        } catch(e: IOException) {
            val errorMessage = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
            emit(Resource.Error(message = UiText.DynamicString(errorMessage)))
        }
    }
}