package com.codmine.mukellef.presentation.notification_screen

import com.codmine.mukellef.domain.model.notifications.ReadingNotification

data class ReadingNotificationState(
    val isLoading: Boolean = false,
    val readingNotification: ReadingNotification? = null,
    val error: String = ""
)
