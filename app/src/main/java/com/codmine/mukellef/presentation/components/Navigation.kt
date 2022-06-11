package com.codmine.mukellef.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_ID
import com.codmine.mukellef.domain.util.Constants.NAV_CHAT_MESSAGES_USER_NAME
import com.codmine.mukellef.presentation.balance_screen.BalanceScreen
import com.codmine.mukellef.presentation.chat_screen.messages.MessagesScreen
import com.codmine.mukellef.presentation.chat_screen.person.PersonScreen
import com.codmine.mukellef.presentation.document_screen.DocumentScreen
import com.codmine.mukellef.presentation.login_screen.LoginScreen
import com.codmine.mukellef.presentation.notification_screen.NotificationScreen
import com.codmine.mukellef.presentation.splash_screen.SplashScreen

@Composable
fun Navigation(
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController, snackbarHostState)
        }
        composable(Screen.NotificationScreen.route) {
            NotificationScreen(paddingValues)
        }
        composable(Screen.DocumentScreen.route) {
            DocumentScreen(paddingValues)
        }
        composable(Screen.ChatPersonScreen.route) {
            PersonScreen(navController, paddingValues)
        }

        composable(Screen.ChatMessageScreen.route + "/{$NAV_CHAT_MESSAGES_USER_ID}/{$NAV_CHAT_MESSAGES_USER_NAME}") {
            MessagesScreen(navController, paddingValues)
        }

        composable(Screen.BalanceScreen.route) {
            BalanceScreen(paddingValues)
        }
    }
}