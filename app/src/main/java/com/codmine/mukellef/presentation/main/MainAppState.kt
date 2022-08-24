package com.codmine.mukellef.presentation.main

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.codmine.mukellef.presentation.components.Screen

@Stable
class MainAppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState
) {
    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(navigateScreen: Screen, popUpScreen: Screen) {
        navController.navigate(navigateScreen.route) {
            launchSingleTop = true
            popUpTo(popUpScreen.route) { inclusive = true }
        }
    }

    fun clearAndNavigate(screen: Screen) {
        navController.navigate(screen.route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}