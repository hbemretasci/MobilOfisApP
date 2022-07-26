package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.codmine.mukellef.domain.util.Resource
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class PostMessage @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(gib: String, sender: String, receiver: String, message: String): Resource<DocumentReference> {
        return repository.postMessage(gib, sender, receiver, message)
    }
}