package com.codmine.mukellef.presentation.notification_screen

import com.codmine.mukellef.domain.model.notifications.Notification

data class NotificationScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val notifications: List<Notification> = emptyList(),
    val error: String = ""
)
