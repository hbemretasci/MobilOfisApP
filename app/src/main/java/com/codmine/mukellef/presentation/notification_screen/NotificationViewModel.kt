package com.codmine.mukellef.presentation.notification_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.datastore.AppSettings
import com.codmine.mukellef.domain.model.notifications.Notification
import com.codmine.mukellef.domain.use_case.files.ShowDocument
import com.codmine.mukellef.domain.use_case.notification_screen.GetNotifications
import com.codmine.mukellef.domain.use_case.notification_screen.PostNotificationReadingInfo
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotifications: GetNotifications,
    private val postNotificationReadingInfo: PostNotificationReadingInfo,
    private val getUserLoginData: GetUserLoginData,
    private val showDocument: ShowDocument
): ViewModel() {
    private val _dataState = mutableStateOf(NotificationScreenDataState())
    val dataState: State<NotificationScreenDataState> = _dataState

    private val _readingNotificationState = mutableStateOf(ReadingNotificationState())
    private val _appSettings = mutableStateOf(AppSettings())

    fun onEvent(event: NotificationEvent) {
        when(event) {
            is NotificationEvent.LoadData -> {
                getAppSettings()
                getNotificationList()
            }
            is NotificationEvent.Refresh -> {
                getNotificationList()
            }
            is NotificationEvent.ReadNotification -> {
                if (event.notification.readingTime.isEmpty()) {
                    postReadingInfo(event.notification)
                }
            }
            is NotificationEvent.ShowNotification -> {
                if (event.notification.documentName.isNotEmpty()) {
                    showNotification(_appSettings.value.gib, event.notification)
                }
            }
        }
    }

    private fun showNotification(gib: String, notification: Notification) {
        showDocument(gib, notification.documentName)
    }

    private fun getNotificationList() {
        getNotifications(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _appSettings.value.accountant
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = NotificationScreenDataState(notifications = result.data ?: emptyList() )
                }
                is Resource.Error -> {
                    _dataState.value = NotificationScreenDataState(
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error)
                    )
                }
                is Resource.Loading -> {
                    _dataState.value = NotificationScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun postReadingInfo(notification: Notification) {
        postNotificationReadingInfo(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, notification.id
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _readingNotificationState.value = ReadingNotificationState(readingNotification = result.data)
                }
                is Resource.Error -> {
                    _readingNotificationState.value = ReadingNotificationState(
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error)
                    )
                }
                is Resource.Loading -> {
                    _readingNotificationState.value = ReadingNotificationState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getAppSettings() {
        getUserLoginData().onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}