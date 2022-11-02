package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.CustomPreferences
import javax.inject.Inject

class SetUserLoginData @Inject constructor(
    private val dataStore: CustomPreferences
) {
    suspend operator fun invoke(
        login: Boolean, gib: String, vk: String, password: String, user: String, title:String, accountant: String
    ) {
        dataStore.saveUserData(login, gib, vk, password, user, title, accountant)
    }
}