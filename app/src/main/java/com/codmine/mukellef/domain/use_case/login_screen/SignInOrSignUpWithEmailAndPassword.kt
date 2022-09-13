package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import javax.inject.Inject

class SignInOrSignUpWithEmailAndPassword @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(email: String, password: String, onResult: (Throwable?) -> Unit) {
        repository.signIn(email, password) { error ->
            if(error != null) {
                println(error)
                println(error.localizedMessage)
            }
        }
    }
}