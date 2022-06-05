package com.codmine.mukellef.presentation.splash_screen

sealed class SplashEvent{
    object LoadData: SplashEvent()
    object ShowLogo: SplashEvent()
    object HideLogo: SplashEvent()
    object Navigate: SplashEvent()
}
