package com.codmine.mukellef.domain.use_case.document_screen

import com.codmine.mukellef.data.remote.dto.post_reading.toReadingDocument
import com.codmine.mukellef.domain.model.documents.ReadingDocument
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PostDocumentReadingInfo @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String, documentId: String
    ): Flow<Resource<ReadingDocument>> = flow {
        try {
            emit(Resource.Loading())
            val documentReadingInfo = repository.postReadingInfo(
                Constants.QUERY_READING, gib, vk, password, documentId
            ).toReadingDocument()
            emit(Resource.Success(documentReadingInfo))
        } catch(e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Beklenmeyen hata."))
        } catch(e: IOException) {
            emit(Resource.Error("Sunucuya erişilemiyor, bağlantı hatası."))
        }
    }
}