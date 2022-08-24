package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.data.remote.dto.tax_payer.toRelatedUser
import com.codmine.mukellef.domain.model.tax_payer.RelatedUser
import com.codmine.mukellef.domain.repository.MobileOfficeRepository
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetRelatedUsers @Inject constructor(
    private val repository: MobileOfficeRepository
) {
    operator fun invoke(
        gib: String, vk: String, password: String): Flow<Resource<List<RelatedUser>>> = flow {
        try {
            emit(Resource.Loading())
            val relatedUsers = repository.getTaxPayer(Constants.QUERY_MUKELLEF, gib, vk, password)
                .user?.relatedUsers?.map { it.toRelatedUser("0") }
            emit(Resource.Success(relatedUsers ?: emptyList()))
        } catch(e: HttpException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        } catch(e: IOException) {
            emit(Resource.Error(message = UiText.StringResources(R.string.server_connection_error)))
        }
    }
}