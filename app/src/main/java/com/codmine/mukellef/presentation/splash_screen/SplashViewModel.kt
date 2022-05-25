package com.codmine.mukellef.presentation.splash_screen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {

    private val _logoState = mutableStateOf(false)
    val logoState: State<Boolean> = _logoState

    fun showLogo() {
        _logoState.value = true
    }
    fun hideLogo() {
        _logoState.value = false
    }
}