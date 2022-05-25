package com.codmine.mukellef.domain.use_case.login_screen

import android.content.Context
import com.codmine.mukellef.data.repository.CustomDataStore
import javax.inject.Inject

class SetUserLoginData @Inject constructor(
    private val dataStore: CustomDataStore
) {
    suspend operator fun invoke(
        login: Boolean, gib: String, vk: String, password: String, user: String, accountant: String, context: Context
    ) {
        dataStore.saveUserData(login, gib, vk, password, user, accountant, context)
    }
}