package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserPlayerId @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(userId: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val result = repository.getUserPlayerId(userId).get("onesignalid").toString()
            emit(Resource.Success(result))
        } catch(e: Exception) {
            emit(Resource.Error(message = UiText.DynamicString(e.localizedMessage)))
        }
    }
}