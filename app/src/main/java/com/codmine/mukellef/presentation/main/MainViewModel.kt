package com.codmine.mukellef.presentation.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.domain.use_case.login_screen.SetUserLoginData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class MainViewModel @Inject constructor(
    private val setUserLoginData: SetUserLoginData
): ViewModel() {
    var exitDialogState by mutableStateOf(false)
    private set

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ExitDialog -> {
                exitDialogState = true
            }
            is MainEvent.ExitCancel -> {
                exitDialogState = false
            }
            is MainEvent.ExitConfirm -> {
                viewModelScope.launch {
                    setAppSettings(false, "", "", "", "", "")
                    exitProcess(0)
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