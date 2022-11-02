package com.codmine.mukellef.domain.repository

interface CustomPreferences {
    suspend fun saveUserData(login: Boolean, gib: String, vk: String, password: String, user: String, title: String, accountant: String)
    suspend fun getLoginData(): Boolean
    suspend fun getGibData(): String
    suspend fun getVkData(): String
    suspend fun getPasswordData(): String
    suspend fun getUserData(): String
    suspend fun getTitleData(): String
    suspend fun getAccountantData(): String

    companion object {
        const val KEY_LOGIN = "login"
        const val KEY_GIB = "gib"
        const val KEY_VK = "vk"
        const val KEY_PASSWORD = "password"
        const val KEY_USER = "user"
        const val KEY_TITLE = "title"
        const val KEY_ACCOUNTANT = "accountant"
        const val DATA_FILE_KEY = "com.codmine.mukellef.SETTINGS"
    }

}