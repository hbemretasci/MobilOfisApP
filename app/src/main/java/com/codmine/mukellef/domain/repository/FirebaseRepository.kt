package com.codmine.mukellef.domain.repository

import com.codmine.mukellef.data.remote.dto.chat.MessageDto
import com.codmine.mukellef.domain.model.chat.Message
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    fun postMessage(gib: String, message: MessageDto, onResult: (Throwable?) -> Unit)

    fun addUser(userId: String, oneSignalPlayerId: String)

    fun getUserPlayerId(userId: String): Flow<String>

    fun addListener(
        gib: String, sender: String, receiver: String, key:String,
        onDocumentEvent: (Message) -> Unit, onError: (Throwable) -> Unit
    )

    fun removeListener()

    fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit)

    fun isLoggedInUser(): Boolean

    fun signOut()

}