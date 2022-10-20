package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class AddOrUpdateUser @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(userId: String, oneSignalPlayerId: String) {
        repository.addUser(userId, oneSignalPlayerId)
    }
}