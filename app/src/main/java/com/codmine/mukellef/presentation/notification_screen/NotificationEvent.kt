package com.codmine.mukellef.presentation.notification_screen

sealed interface NotificationEvent {
    object LoadData: NotificationEvent
}