package com.codmine.mukellef.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codmine.mukellef.domain.repository.CustomPreferences
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_GIB
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_LOGIN
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_PASSWORD
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_TITLE
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_USER
import com.codmine.mukellef.domain.repository.CustomPreferences.Companion.KEY_VK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class DatastorePreferences(
    private val dataStorePreferences: DataStore<Preferences>
): CustomPreferences {

    override suspend fun saveUserData(
        login: Boolean,
        gib: String,
        vk: String,
        password: String,
        user: String,
        title: String,
        accountant: String
    ) {
        val loginKey = booleanPreferencesKey(KEY_LOGIN)
        val gibKey = stringPreferencesKey(KEY_GIB)
        val vkKey = stringPreferencesKey(KEY_VK)
        val passwordKey = stringPreferencesKey(KEY_PASSWORD)
        val userKey = stringPreferencesKey(KEY_USER)
        val titleKey = stringPreferencesKey(KEY_TITLE)
        val accountantKey = stringPreferencesKey(KEY_ACCOUNTANT)
        dataStorePreferences.edit {
            it[loginKey] = login
            it[gibKey] = gib
            it[vkKey] = vk
            it[passwordKey] = password
            it[userKey] = user
            it[titleKey] = title
            it[accountantKey] = accountant
        }
    }

    override suspend fun getLoginData(): Boolean {
        val loginKey = booleanPreferencesKey(KEY_LOGIN)
        val valueFlow: Flow<Boolean> = dataStorePreferences.data.map {
            it[loginKey] ?: false
        }
        return valueFlow.first()
    }

    override suspend fun getGibData(): String {
        val gibKey = stringPreferencesKey(KEY_GIB)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[gibKey] ?: ""
        }
        return valueFlow.first()
    }

    override suspend fun getVkData(): String {
        val vkKey = stringPreferencesKey(KEY_VK)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[vkKey] ?: ""
        }
        return valueFlow.first()
    }

    override suspend fun getPasswordData(): String {
        val passwordKey = stringPreferencesKey(KEY_PASSWORD)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[passwordKey] ?: ""
        }
        return valueFlow.first()
    }

    override suspend fun getUserData(): String {
        val userKey = stringPreferencesKey(KEY_USER)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[userKey] ?: ""
        }
        return valueFlow.first()
    }

    override suspend fun getTitleData(): String {
        val titleKey = stringPreferencesKey(KEY_TITLE)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[titleKey] ?: ""
        }
        return valueFlow.first()
    }

    override suspend fun getAccountantData(): String {
        val accountantKey = stringPreferencesKey(KEY_ACCOUNTANT)
        val valueFlow: Flow<String> = dataStorePreferences.data.map {
            it[accountantKey] ?: ""
        }
        return valueFlow.first()
    }

}