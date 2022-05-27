package com.codmine.mukellef.presentation.notification_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.use_case.notification_screen.GetNotifications
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Constants.KEY_ACCOUNTANT
import com.codmine.mukellef.domain.util.Constants.KEY_GIB
import com.codmine.mukellef.domain.util.Constants.KEY_PASSWORD
import com.codmine.mukellef.domain.util.Constants.KEY_USER
import com.codmine.mukellef.domain.util.Constants.KEY_VK
import com.codmine.mukellef.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotifications: GetNotifications,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    private val _dataState = mutableStateOf(NotificationScreenDataState())
    val dataState: State<NotificationScreenDataState> = _dataState

    private val _appSettings = mutableStateOf(AppSettings())
    val appSettings: State<AppSettings> = _appSettings

    private fun getNotificationList() {
        getNotifications(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _appSettings.value.accountant
        ).onEach { result ->
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

    fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}