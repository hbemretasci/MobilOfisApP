package com.codmine.mukellef.presentation.notification_screen

import com.codmine.mukellef.domain.model.notifications.Notification
import com.codmine.mukellef.presentation.util.UiText

data class NotificationScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val notifications: List<Notification> = emptyList()
)
