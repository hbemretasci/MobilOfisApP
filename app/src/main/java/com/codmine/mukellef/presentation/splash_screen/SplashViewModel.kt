package com.codmine.mukellef.presentation.splash_screen

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    private val _logoState = mutableStateOf(false)
    val logoState: State<Boolean> = _logoState

    private val _appSettings = mutableStateOf(AppSettings())
    val appSettings: State<AppSettings> = _appSettings

    fun showLogo() {
        _logoState.value = true
    }
    fun hideLogo() {
        _logoState.value = false
    }

    suspend fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }
}