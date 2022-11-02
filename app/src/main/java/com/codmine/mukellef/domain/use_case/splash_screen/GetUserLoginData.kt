package com.codmine.mukellef.domain.use_case.splash_screen

import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.repository.CustomPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserLoginData @Inject constructor(
    private val dataStore: CustomPreferences
) {
    operator fun invoke(): Flow<AppSettings> = flow {
        val login = dataStore.getLoginData()
        val gib = dataStore.getGibData()
        val vk = dataStore.getVkData()
        val password = dataStore.getPasswordData()
        val user = dataStore.getUserData()
        val title = dataStore.getTitleData()
        val accountant = dataStore.getAccountantData()
        val appSettings = AppSettings(login, gib, vk, password, user, title, accountant)
        emit(appSettings)
    }
}