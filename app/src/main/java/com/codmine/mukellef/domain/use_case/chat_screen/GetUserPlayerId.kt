package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserPlayerId @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(userId: String): String {
        return repository.getUserPlayerId(userId)
    }
}