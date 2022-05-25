package com.codmine.mukellef.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codmine.mukellef.presentation.login_screen.LoginScreen
import com.codmine.mukellef.presentation.splash_screen.SplashScreen

@Composable
fun Navigation(
    navController: NavController,
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
            LoginScreen(navController = navController)
        }

        composable(Screen.Notification.route + "/{gib}/{vk}/{pass}/{user}/{accountant}") {
            //NotificationScreen(paddingValues)
        }


        /*
        composable(
            route = Screen.Notification.route + "/{gib}/{vk}/{pass}/{user}/{accountant}",
            arguments = listOf(
            navArgument("gib") { type = NavType.StringType },
            navArgument("vk") { type = NavType.StringType },
            navArgument("pass") { type = NavType.StringType },
            navArgument("user") { type = NavType.StringType },
            navArgument("accountant") { type = NavType.StringType }
            )
        ) {
            val gibNum = remember { it.arguments?.getString("gib") }
            val vkNum = remember { it.arguments?.getString("vk") }
            val passText = remember { it.arguments?.getString("pass") }
            val userId = remember { it.arguments?.getString("user") }
            val accountantId = remember { it.arguments?.getString("accountant") }
            NotificationScreen(gibNum?: "", vkNum?: "", passText?: "",userId?: "",accountantId?: "", paddingValues)
        }

         */



//        composable(Screen.Document.route) { LoginScreen(navController = navController) }
//        composable(Screen.ChatPerson.route) { LoginScreen(navController = navController) }
//        composable(Screen.ChatMessage.route) { LoginScreen(navController = navController) }
//        composable(Screen.Balance.route) { LoginScreen(navController = navController) }
    }

}