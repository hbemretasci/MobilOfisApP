package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import javax.inject.Inject

class LogInWithEmailAndPassword @Inject constructor(
    private val repository: FirebaseRepository
) {
    operator fun invoke(email: String, password: String, onSuccess: () -> Unit, onError: (Throwable?) -> Unit) {
        repository.signIn(email, password) { signInError ->
            if (signInError != null) {
                if ((signInError is FirebaseAuthInvalidUserException) && (signInError.errorCode == "ERROR_USER_NOT_FOUND")) {
                    repository.signUp(email, password) { signUpError ->
                        if (signUpError == null) onSuccess() else onError(signUpError)
                    }
                } else onError(signInError)
            } else onSuccess()
        }
    }
}