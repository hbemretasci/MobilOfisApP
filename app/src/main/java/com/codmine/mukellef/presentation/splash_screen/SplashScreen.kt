package com.codmine.mukellef.presentation.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.Constants.LOGO_DISPLAY_TIME
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.presentation.util.UiText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val logoState = viewModel.logoState.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(SplashEvent.LoadData)
        viewModel.onEvent(SplashEvent.ShowLogo)
        delay(LOGO_DISPLAY_TIME)
        viewModel.onEvent(SplashEvent.HideLogo)
        viewModel.onEvent(SplashEvent.Navigate)
    }

    LaunchedEffect(key1 = context) {
        viewModel.uiEvents.collect { event ->
            when(event) {
                is SplashUiEvent.NavigateLogin -> {
                    navController.navigate(Screen.LoginScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
                is SplashUiEvent.NavigateNotification -> {
                    navController.navigate(Screen.NotificationScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ShowLogo(logoState)
    }
}

@Composable
fun ShowLogo(shown: Boolean) {
    AnimatedVisibility(
        visible = shown,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
        ),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_image),
            contentDescription = UiText.StringResources(R.string.app_logo).asString()
        )
    }
}