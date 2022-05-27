package com.codmine.mukellef.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.codmine.mukellef.domain.util.Constants.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.util.Constants.KEY_GIB
import com.codmine.mukellef.domain.util.Constants.KEY_PASSWORD
import com.codmine.mukellef.domain.util.Constants.KEY_USER
import com.codmine.mukellef.domain.util.Constants.KEY_VK
import com.codmine.mukellef.presentation.balance_screen.BalanceScreen
import com.codmine.mukellef.presentation.chat_screen.ChatPersonScreen
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
            SplashScreen(navController = navController)
        }

        composable(Screen.LoginScreen.route) {
            LoginScreen(navController = navController, snackbarHostState)
        }

        composable(Screen.NotificationScreen.route + Screen.NotificationScreen.arg, listOf(
                navArgument(KEY_GIB) { type = NavType.StringType },
                navArgument(KEY_VK) { type = NavType.StringType },
                navArgument(KEY_PASSWORD) { type = NavType.StringType },
                navArgument(KEY_USER) { type = NavType.StringType },
                navArgument(KEY_ACCOUNTANT) { type = NavType.StringType }
            )
        ) {
            val gib = remember { it.arguments?.getString(KEY_GIB) }
            val vk = remember { it.arguments?.getString(KEY_VK) }
            val password = remember { it.arguments?.getString(KEY_PASSWORD) }
            val user = remember { it.arguments?.getString(KEY_USER) }
            val accountant = remember { it.arguments?.getString(KEY_ACCOUNTANT) }
            NotificationScreen(navController, paddingValues)
        }

        composable(Screen.DocumentScreen.route) { DocumentScreen(navController = navController, paddingValues) }
        composable(Screen.ChatPersonScreen.route) { ChatPersonScreen(navController = navController, paddingValues) }
//        composable(Screen.ChatMessage.route) { LoginScreen(navController = navController) }
        composable(Screen.BalanceScreen.route) { BalanceScreen(navController = navController, paddingValues) }
    }

}