package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class RemoveListener @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke() {
        repository.removeListener()
    }
}