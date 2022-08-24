package com.codmine.mukellef.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.presentation.balance_screen.BalanceScreen
import com.codmine.mukellef.presentation.chat_screen.messages.MessagesScreen
import com.codmine.mukellef.presentation.chat_screen.person.PersonScreen
import com.codmine.mukellef.presentation.document_screen.DocumentScreen
import com.codmine.mukellef.presentation.login_screen.LoginScreen
import com.codmine.mukellef.presentation.main.MainAppState
import com.codmine.mukellef.presentation.notification_screen.NotificationScreen
import com.codmine.mukellef.presentation.splash_screen.SplashScreen
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.statusBarsPadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfisScaffold(
    appState: MainAppState,
    showNavigation: Boolean = false,
    showBars: Boolean = true,
    onActIconPressed: () -> Unit,
    modifier: Modifier
) {
    Scaffold(
        modifier = modifier
            .navigationBarsPadding()
            .statusBarsPadding(),
        topBar = {
             if(showBars) OfisTopBar(
                 navigation = {
                     if(showNavigation) {
                         IconButton(onClick = { appState.popUp() }) {
                             Icon(
                                 imageVector = Icons.Filled.ArrowBack,
                                 contentDescription = UiText.StringResources(R.string.top_bar_back_button_content_description).asString()
                             )
                         }
                     }
                 },
                 title = {
                     Text(
                         text = UiText.StringResources(R.string.app_name).asString(),
                         color = MaterialTheme.colorScheme.onSecondaryContainer,
                         style = MaterialTheme.typography.titleLarge,
                     )
                 },
                 actions = {
                     IconButton(
                         onClick = onActIconPressed
                     ) {
                         Icon(
                             imageVector = Icons.Filled.ExitToApp,
                             tint = MaterialTheme.colorScheme.onSecondaryContainer,
                             contentDescription = UiText.StringResources(R.string.exit_app).asString(),
                         )
                     }
                 }
             )
        },
        bottomBar = { if(showBars) OfisBottomBar(appState.navController, showBars) },
        snackbarHost = { SnackbarHost(appState.snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = appState.navController,
            startDestination = Screen.SplashScreen.route
        ) {
            composable(Screen.SplashScreen.route) {
                SplashScreen(
                    openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
                )
            }
            composable(Screen.LoginScreen.route) {
                LoginScreen(
                    openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) },
                    snackbarHostState = appState.snackbarHostState
                )
            }
            composable(Screen.NotificationScreen.route) {
                NotificationScreen(paddingValues)
            }
            composable(Screen.DocumentScreen.route) {
                DocumentScreen(paddingValues)
            }
            composable(Screen.ChatPersonScreen.route) {
                PersonScreen(
                    open = { route -> appState.navigate(route) },
                    paddingValues = paddingValues
                )
            }
            composable(Screen.ChatMessageScreen.route +
                    "/{${Constants.NAV_CHAT_MESSAGES_USER_ID}}/{${Constants.NAV_CHAT_MESSAGES_USER_NAME}}") {
                MessagesScreen(
                    paddingValues = paddingValues,
                    snackbarHostState = appState.snackbarHostState,
                )
            }
            composable(Screen.BalanceScreen.route) {
                BalanceScreen(paddingValues)
            }
        }
    }
}