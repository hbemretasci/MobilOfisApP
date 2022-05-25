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
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(Screen.Login.route) {
            LoginScreen(navController = navController, snackbarHostState)
        }

        composable(
            Screen.Notification.route + "/{gib}/{vk}/{pass}/{user}/{accountant}",
            listOf(
                navArgument("gib") { type = NavType.StringType },
                navArgument("vk") { type = NavType.StringType },
                navArgument("pass") { type = NavType.StringType },
                navArgument("user") { type = NavType.StringType },
                navArgument("accountant") { type = NavType.StringType }
            )
        ) {
            val gib = remember { it.arguments?.getString("gib") }
            val vk = remember { it.arguments?.getString("vk") }
            val password = remember { it.arguments?.getString("pass") }
            val user = remember { it.arguments?.getString("user") }
            val accountant = remember { it.arguments?.getString("accountant") }
            NotificationScreen(
                gib = gib ?: "",
                vk = vk ?: "",
                password = password ?: "",
                user = user ?: "",
                accountant = accountant ?: "",
                navController =  navController,
                paddingValues =  paddingValues
            )
        }

//        composable(Screen.Document.route) { LoginScreen(navController = navController) }
//        composable(Screen.ChatPerson.route) { LoginScreen(navController = navController) }
//        composable(Screen.ChatMessage.route) { LoginScreen(navController = navController) }
//        composable(Screen.Balance.route) { LoginScreen(navController = navController) }
    }

}