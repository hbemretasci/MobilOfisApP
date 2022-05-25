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
//    val logoState = viewModel.logoState.value
//    val appSettings = viewModel.appSettings.value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.getAppSettings(context)
        viewModel.showLogo()
        delay(LOGO_DISPLAY_TIME)
        viewModel.hideLogo()
        if(viewModel.appSettings.value.login) {
            navController.navigate(
                Screen.Notification.route +
                        "/${viewModel.appSettings.value.gib}/${viewModel.appSettings.value.vk}/${viewModel.appSettings.value.password}/${viewModel.appSettings.value.user}/${viewModel.appSettings.value.accountant}"
            ) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        } else {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Splash.route) {
                    inclusive = true
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        ShowLogo(viewModel.logoState.value)
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