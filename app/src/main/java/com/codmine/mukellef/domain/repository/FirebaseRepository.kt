package com.codmine.mukellef.domain.repository

import com.codmine.mukellef.domain.util.Resource
import com.google.firebase.firestore.DocumentReference

interface FirebaseRepository {

    fun postMessage(
        gib: String,
        sender: String,
        receiver: String,
        messageContent: String
    ): Resource<DocumentReference>

}