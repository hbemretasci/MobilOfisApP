package com.codmine.mukellef.domain.repository

import com.codmine.mukellef.data.remote.dto.chat.MessageDto
import com.codmine.mukellef.domain.model.chat.Message

interface FirebaseRepository {

    fun postMessage(gib: String, message: MessageDto, onResult: (Throwable?) -> Unit)

    fun addListener(
        gib: String, sender: String, receiver: String, onDocumentEvent: (Message) -> Unit, onError: (Throwable) -> Unit
    )

    fun removeListener()

}