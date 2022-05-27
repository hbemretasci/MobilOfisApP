package com.codmine.mukellef.presentation.components

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash_screen")
    object LoginScreen: Screen("login_screen")
    object NotificationScreen: Screen("notification_screen")
    object DocumentScreen: Screen("document_screen")
    object ChatPersonScreen: Screen("chat_person_screen")
    object ChatMessageScreen: Screen("chat_messages_screen")
    object BalanceScreen: Screen("balance_screen")
}