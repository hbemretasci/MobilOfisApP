package com.codmine.mukellef.domain.use_case.splash_screen

import android.content.Context
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.data.repository.CustomDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserLoginData @Inject constructor(
    private val dataStore: CustomDataStore
) {
    operator fun invoke(context: Context): Flow<AppSettings> = flow {
        val login = dataStore.getLoginData(context)
        val gib = dataStore.getGibData(context)
        val vk = dataStore.getVkData(context)
        val password = dataStore.getPasswordData(context)
        val user = dataStore.getUserData(context)
        val accountant = dataStore.getAccountantData(context)
        val appSettings = AppSettings(login, gib, vk, password, user, accountant)
        emit(appSettings)
    }
}