package com.codmine.mukellef.presentation.main

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _exitDialogState = mutableStateOf(false)
    val exitDialogState: State<Boolean> = _exitDialogState

    fun onEvent(event: MainEvent) {
        when(event) {
            is MainEvent.ExitDialog -> {
                _exitDialogState.value = true
            }
            is MainEvent.ExitCancel -> {
                _exitDialogState.value = false
            }
            is MainEvent.ExitConfirm -> {
                viewModelScope.launch {
                    setAppSettings(false, "", "", "", "", "")
                    exitProcess(0)
                }
            }
        }
    }

    private suspend fun setAppSettings(loginStatus: Boolean, gib: String, vk: String, password: String,
                                       user: String, accountant: String
    ) {
        setUserLoginData(loginStatus, gib, vk, password, user, accountant)
    }

}