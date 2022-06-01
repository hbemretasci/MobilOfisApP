package com.codmine.mukellef.presentation.notification_screen

import com.codmine.mukellef.domain.model.notifications.Notification

sealed class NotificationEvent{
    object LoadData: NotificationEvent()
    object Refresh: NotificationEvent()
    data class ReadNotification(val notification: Notification): NotificationEvent()
    data class ShowNotification(val notification: Notification): NotificationEvent()
}
