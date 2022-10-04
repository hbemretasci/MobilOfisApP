package com.codmine.mukellef.domain.use_case.splash_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class GetUserLoginInformation @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(): Boolean {
        return repository.isLoggedInUser()
    }
}