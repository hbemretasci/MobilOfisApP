package com.codmine.mukellef.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.domain.use_case.login_screen.SetUserLoginData
import com.codmine.mukellef.domain.use_case.main.LogOut
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setUserLoginData: SetUserLoginData,
    private val logOut: LogOut
): ViewModel() {
    var exitDialogState by mutableStateOf(false)
    private set

    private val _uiEventChannel = Channel<MainUiEvent>()
    val uiEvents = _uiEventChannel.receiveAsFlow()

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ExitDialog -> {
                exitDialogState = true
            }
            is MainEvent.ExitCancel -> {
                exitDialogState = false
            }
            is MainEvent.ExitConfirm -> {
                exitDialogState = false
                viewModelScope.launch {
                    setAppSettings(
                        loginStatus = false,
                        gib = "",
                        vk = "",
                        password = "",
                        user = "",
                        accountant = ""
                    )
                    logOut()
                    _uiEventChannel.send(MainUiEvent.Logout)
                }
            }
        }
    }

    private suspend fun setAppSettings(
        loginStatus: Boolean, gib: String, vk: String, password: String, user: String, accountant: String
    ) {
        setUserLoginData(loginStatus, gib, vk, password, user, accountant)
    }

}