package com.codmine.mukellef.presentation.notification_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var uiState by mutableStateOf(NotificationScreenDataState())
        private set

    var documentReadingState by mutableStateOf(ReadingNotificationState())
        private set

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
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = false,
                        notifications = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        notifications = emptyList()
                    )
                }
                is Resource.Loading -> {
                    uiState = uiState.copy(
                        isLoading = true,
                        errorStatus = false,
                        notifications = emptyList()
                    )
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
                    documentReadingState = documentReadingState.copy(
                        isLoading = false,
                        errorStatus = false,
                        readingNotification = result.data
                    )
                }
                is Resource.Error -> {
                    documentReadingState = documentReadingState.copy(
                        isLoading = false,
                        errorStatus = true,
                        errorText = result.message ?: UiText.StringResources(R.string.unexpected_error),
                        readingNotification = null
                    )
                }
                is Resource.Loading -> {
                    documentReadingState = documentReadingState.copy(
                        isLoading = true,
                        errorStatus = false,
                        readingNotification = null
                    )
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