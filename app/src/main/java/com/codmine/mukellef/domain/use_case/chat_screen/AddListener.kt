package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class AddListener @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(
        gib: String, sender: String, receiver: String, key: String, onDocumentEvent: (Message) -> Unit, onError: (Throwable) -> Unit
    ) {
        repository.addListener(gib, sender, receiver, key, onDocumentEvent, onError)
    }
}