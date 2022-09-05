package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.data.remote.dto.chat.MessageDto
import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.google.firebase.Timestamp
import javax.inject.Inject

class PostMessage @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(gib: String, sender: String, receiver: String, key:String, message: String, onResult: (Throwable?) -> Unit) {
        val messageItem = MessageDto(message, key, receiver, sender, Timestamp.now())
        repository.postMessage(gib, messageItem, onResult)
    }
}