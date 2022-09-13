package com.codmine.mukellef.data.repository

import com.codmine.mukellef.data.remote.dto.chat.MessageDto
import com.codmine.mukellef.data.remote.dto.chat.toMessage
import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.codmine.mukellef.domain.util.Constants.CHAT_QUERY_LIMIT
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl: FirebaseRepository {

    private val db = Firebase.firestore
    private var listenerRegistration: ListenerRegistration? = null

    private val auth = Firebase.auth

    override fun postMessage(gib: String, message: MessageDto, onResult: (Throwable?) -> Unit) {
        db.collection("MM$gib")
            .add(message)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun addListener(
        gib: String, sender: String, receiver: String, key:String, onDocumentEvent: (Message) -> Unit, onError: (Throwable) -> Unit
    ) {
        val query = db.collection("MM$gib")
            .whereEqualTo("key", key)
            .orderBy("time", Query.Direction.DESCENDING)
            .limit(CHAT_QUERY_LIMIT)
        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error != null) {
                onError(error)
                return@addSnapshotListener
            }
            value?.documentChanges?.forEach {
                val message = it.document.toObject<MessageDto>().toMessage().copy(id = it.document.id)
                onDocumentEvent(message)
            }
        }
    }

    override fun removeListener() {
        listenerRegistration?.remove()
    }

    override fun signUp(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signIn(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
    }

    override fun signOut() {
        auth.signOut()
    }
}