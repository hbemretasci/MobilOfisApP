package com.codmine.mukellef.presentation.splash_screen

import android.content.Context
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
import androidx.datastore.dataStore
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codmine.mukellef.R
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.data.repository.AppSettingsSerializer
import com.codmine.mukellef.domain.util.Constants
import com.codmine.mukellef.domain.util.Constants.LOGO_DISPLAY_TIME
import com.codmine.mukellef.presentation.components.Screen
import com.codmine.mukellef.presentation.util.UiText
import kotlinx.coroutines.delay

val Context.dataStore by dataStore(Constants.DATA_FILE_KEY, AppSettingsSerializer)

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val logoState = viewModel.logoState.value
    val context = LocalContext.current

    val appSettings = context.dataStore.data.collectAsState(
        initial = AppSettings()
    ).value

    LaunchedEffect(key1 = true) {
        viewModel.showLogo()
        delay(LOGO_DISPLAY_TIME)
        viewModel.hideLogo()
        if(appSettings.loginData) {
            navController.navigate(
                Screen.Notification.route +
                        "/${appSettings.gibData}/${appSettings.vkData}/${appSettings.passwordData}/${appSettings.userData}/${appSettings.accountantData}"
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