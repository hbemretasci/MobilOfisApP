package com.codmine.mukellef.presentation.notification_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.domain.use_case.notification_screen.GetNotifications
import com.codmine.mukellef.domain.util.Constants.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.util.Constants.KEY_GIB
import com.codmine.mukellef.domain.util.Constants.KEY_PASSWORD
import com.codmine.mukellef.domain.util.Constants.KEY_USER
import com.codmine.mukellef.domain.util.Constants.KEY_VK
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.presentation.login_screen.LoginEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotifications: GetNotifications,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _dataState = mutableStateOf(NotificationScreenDataState())
    val dataState: State<NotificationScreenDataState> = _dataState

    val gib: String
    val vk: String
    val password: String
    val user: String
    val accountant: String

    init {
        gib = savedStateHandle.get<String>(KEY_GIB)?: ""
        vk = savedStateHandle.get<String>(KEY_VK)?: ""
        password = savedStateHandle.get<String>(KEY_PASSWORD)?: ""
        user = savedStateHandle.get<String>(KEY_USER)?: ""
        accountant = savedStateHandle.get<String>(KEY_ACCOUNTANT)?: ""
        getNotificationList()
    }

    fun getNotificationList() {
        getNotifications(gib, vk, password, user, accountant).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = NotificationScreenDataState(notifications = result.data ?: emptyList() )
                }
                is Resource.Error -> {
                    _dataState.value = NotificationScreenDataState(error = result.message ?: "Beklenmeyen hata.")
                }
                is Resource.Loading -> {
                    _dataState.value = NotificationScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: NotificationEvent, context: Context) {
        when(event) {
            is NotificationEvent.LoadData -> {
                getNotificationList()
            }

        }
    }

}