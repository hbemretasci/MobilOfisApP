package com.codmine.mukellef.presentation.components

sealed class Screen(val route: String) {
    object Splash: Screen("splash_screen_route")
    object Login: Screen("login_screen_route")
    object Notification: Screen("notification_screen_route")
    object Document: Screen("document_screen_route")
    object ChatPerson: Screen("chat_person_screen_route")
    object ChatMessage: Screen("chat_messages_screen_route")
    object Balance: Screen("balance_screen_route")
}
