package com.codmine.mukellef.presentation.components

import com.codmine.mukellef.domain.util.Constants.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.util.Constants.KEY_GIB
import com.codmine.mukellef.domain.util.Constants.KEY_PASSWORD
import com.codmine.mukellef.domain.util.Constants.KEY_USER
import com.codmine.mukellef.domain.util.Constants.KEY_VK

sealed class Screen(val route: String, val arg: String) {
    object SplashScreen: Screen("splash_screen", "")
    object LoginScreen: Screen("login_screen", "")
    object NotificationScreen: Screen(
        "notification_screen",
        "/{$KEY_GIB}/{$KEY_VK}/{$KEY_PASSWORD}/{$KEY_USER}/{$KEY_ACCOUNTANT}"
    )
    object DocumentScreen: Screen("document_screen", "")
    object ChatPersonScreen: Screen("chat_person_screen", "")
    object ChatMessageScreen: Screen("chat_messages_screen", "")
    object BalanceScreen: Screen("balance_screen", "")
}