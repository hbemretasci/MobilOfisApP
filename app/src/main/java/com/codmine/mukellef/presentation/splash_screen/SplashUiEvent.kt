package com.codmine.mukellef.presentation.splash_screen

sealed class SplashUiEvent{
    object NavigateLogin: SplashUiEvent()
    object NavigateNotification: SplashUiEvent()
}
