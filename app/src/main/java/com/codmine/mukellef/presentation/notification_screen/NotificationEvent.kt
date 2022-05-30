package com.codmine.mukellef.presentation.notification_screen

sealed class NotificationEvent{
    object LoadData: NotificationEvent()
    object LoadNotifications: NotificationEvent()
    object Refresh: NotificationEvent()
}
