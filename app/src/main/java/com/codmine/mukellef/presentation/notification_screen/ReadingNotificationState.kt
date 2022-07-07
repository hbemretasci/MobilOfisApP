package com.codmine.mukellef.presentation.notification_screen

import com.codmine.mukellef.domain.model.notifications.ReadingNotification
import com.codmine.mukellef.presentation.util.UiText

data class ReadingNotificationState(
    val isLoading: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val readingNotification: ReadingNotification? = null
)
