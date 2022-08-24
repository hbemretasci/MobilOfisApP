package com.codmine.mukellef.data.repository

import com.codmine.mukellef.data.remote.dto.chat.MessageDto
import com.codmine.mukellef.data.remote.dto.chat.toMessage
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl: FirebaseRepository {

    private val db = Firebase.firestore
    private var listenerRegistration: ListenerRegistration? = null

    override fun postMessage(gib: String, message: MessageDto, onResult: (Throwable?) -> Unit) {
        db.collection("MM$gib")
            .add(message)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun addListener(
        gib: String, sender: String, receiver: String, onDocumentEvent: (Message) -> Unit, onError: (Throwable) -> Unit
    ) {
        val query = db.collection("MM$gib")
            .whereEqualTo("receiver", receiver)
            .whereEqualTo("sender", sender)
            .orderBy("time", Query.Direction.DESCENDING)
        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                //val message = it.document.toObject<MessageDto>().toMessage().copy(id = it.document.id)
                val message = it.document.toObject<MessageDto>().toMessage()
                onDocumentEvent(message)
            }
        }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }
}