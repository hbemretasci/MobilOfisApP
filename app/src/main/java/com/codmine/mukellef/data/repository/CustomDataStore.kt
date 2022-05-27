package com.codmine.mukellef.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codmine.mukellef.domain.util.Constants.DATA_FILE_KEY
import com.codmine.mukellef.domain.util.Constants.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.util.Constants.KEY_GIB
import com.codmine.mukellef.domain.util.Constants.KEY_LOGIN
import com.codmine.mukellef.domain.util.Constants.KEY_PASSWORD
import com.codmine.mukellef.domain.util.Constants.KEY_USER
import com.codmine.mukellef.domain.util.Constants.KEY_VK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CustomDataStore @Inject constructor()  {

    companion object {
        val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_FILE_KEY)
    }

    suspend fun saveUserData(
        login: Boolean, gib: String, vk: String, password: String, user: String, accountant: String, context: Context
    ) {
        val loginKey = booleanPreferencesKey(KEY_LOGIN)
        val gibKey = stringPreferencesKey(KEY_GIB)
        val vkKey = stringPreferencesKey(KEY_VK)
        val passwordKey = stringPreferencesKey(KEY_PASSWORD)
        val userKey = stringPreferencesKey(KEY_USER)
        val accountantKey = stringPreferencesKey(KEY_ACCOUNTANT)
        context.dataStore.edit {
            it[loginKey] = login
            it[gibKey] = gib
            it[vkKey] = vk
            it[passwordKey] = password
            it[userKey] = user
            it[accountantKey] = accountant
        }
    }

    suspend fun getLoginData(context: Context): Boolean {
        val loginKey = booleanPreferencesKey(KEY_LOGIN)
        val valueFlow: Flow<Boolean> = context.dataStore.data.map {
            it[loginKey] ?: false
        }
        return valueFlow.first()
    }

    suspend fun getGibData(context: Context): String {
        val gibKey = stringPreferencesKey(KEY_GIB)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[gibKey] ?: ""
        }
        return valueFlow.first()
    }

    suspend fun getVkData(context: Context): String {
        val vkKey = stringPreferencesKey(KEY_VK)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[vkKey] ?: ""
        }
        return valueFlow.first()
    }

    suspend fun getPasswordData(context: Context): String {
        val passwordKey = stringPreferencesKey(KEY_PASSWORD)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[passwordKey] ?: ""
        }
        return valueFlow.first()
    }

    suspend fun getUserData(context: Context): String {
        val userKey = stringPreferencesKey(KEY_USER)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[userKey] ?: ""
        }
        return valueFlow.first()
    }

    suspend fun getAccountantData(context: Context): String {
        val accountantKey = stringPreferencesKey(KEY_ACCOUNTANT)
        val valueFlow: Flow<String> = context.dataStore.data.map {
            it[accountantKey] ?: ""
        }
        return valueFlow.first()
    }

}