package com.codmine.mukellef.presentation.splash_screen

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    var logoState by mutableStateOf(false)
        private set

    private val _appSettings = mutableStateOf(AppSettings())

    private val _uiEventChannel = Channel<SplashUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: SplashEvent) {
        when(event) {
            is SplashEvent.LoadData -> {
                getAppSettings()
            }
            is SplashEvent.ShowLogo -> {
                logoState = true
            }
            is SplashEvent.HideLogo -> {
                logoState = false
            }
            is SplashEvent.Navigate -> {
                if(_appSettings.value.login) {
                    viewModelScope.launch {
                        _uiEventChannel.send(SplashUiEvent.NavigateNotification)
                    }
                } else {
                    viewModelScope.launch {
                        _uiEventChannel.send(SplashUiEvent.NavigateLogin)
                    }
                }
            }
        }
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }
}