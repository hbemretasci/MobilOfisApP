package com.codmine.mukellef.data.repository

import com.codmine.mukellef.R
import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseRepositoryImpl: FirebaseRepository {

    private val db = Firebase.firestore

    override fun postMessage(gib: String, sender: String, receiver: String, messageContent: String): Resource<DocumentReference> {

        val chatItem = hashMapOf(
            "sender" to sender,
            "receiver" to receiver,
            "time" to Timestamp.now(),
            "status" to false,
            "content" to messageContent
        )

        var errorMessage: UiText? = null
        var document: DocumentReference? = null

        db.collection("MM$gib")
            .add(chatItem)
            .addOnSuccessListener { documentReference ->
                document = documentReference
            }
            .addOnFailureListener { e ->
                val message = (e.localizedMessage ?: UiText.StringResources(R.string.unexpected_error)) as String
                errorMessage = UiText.DynamicString(message)
            }
        document?.let {
            return Resource.Success(data = it)
        }
        errorMessage?.let {
            return Resource.Error(message = it)
        }
        return Resource.Error(message = UiText.StringResources(R.string.unexpected_error))
    }

}