package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserPlayerId @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(userId: String): Flow<String> {
        return repository.getUserPlayerId(userId)
    }
}