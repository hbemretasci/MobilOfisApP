package com.codmine.mukellef.domain.use_case.main

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class LogOut @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke() {
        if (repository.isLoggedInUser()) repository.signOut()
    }
}