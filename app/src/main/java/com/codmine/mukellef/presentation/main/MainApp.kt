package com.codmine.mukellef.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.presentation.components.ExitDialog
import com.codmine.mukellef.presentation.components.OfisScaffold
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.ui.theme.MobilOfisTheme
import com.google.accompanist.insets.ProvideWindowInsets

@Composable
fun MainApp() {

    val viewModel: MainViewModel = hiltViewModel()
    val openExitAppDialog = viewModel.exitDialogState
    val appState = rememberAppState()

    LaunchedEffect(key1 = true) {
        viewModel.uiEvents.collect { event ->
            when(event) {
                is MainUiEvent.Logout -> {
                    appState.clearAndNavigate(Screen.LoginScreen)
                }
            }
        }
    }

    MobilOfisTheme {
        ProvideWindowInsets(
            windowInsetsAnimationsEnabled = true,
            consumeWindowInsets = false
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
            ) {
                OfisScaffold(
                    appState = appState,
                    showNavigation = shouldShowNaviIcon(appState.navController) ,
                    showBars = !shouldNotShowBars(appState.navController),
                    onActIconPressed = { viewModel.onEvent(MainEvent.ExitDialog) },
                    modifier = Modifier.fillMaxSize()
                )
                if (openExitAppDialog) {
                    ExitDialog(
                        onConfirmClick = { viewModel.onEvent(MainEvent.ExitConfirm) },
                        onDismissClick = { viewModel.onEvent(MainEvent.ExitCancel) }
                    )
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState:  SnackbarHostState = remember { SnackbarHostState() }
) = remember(navController, snackbarHostState) {
    MainAppState(navController, snackbarHostState)
}

@Composable
private fun shouldNotShowBars(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    return currentDestination?.route in listOf(
        Screen.SplashScreen.route,
        Screen.LoginScreen.route,
    )
}

@Composable
private fun shouldShowNaviIcon(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    return currentDestination?.route in listOf(
        Screen.ChatMessageScreen.route + "/{${Constants.NAV_CHAT_MESSAGES_USER_ID}}/{${Constants.NAV_CHAT_MESSAGES_USER_NAME}}"
    )
}