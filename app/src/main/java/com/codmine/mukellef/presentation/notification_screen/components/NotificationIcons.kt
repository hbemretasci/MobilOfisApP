package com.codmine.mukellef.presentation.notification_screen.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MarkChatRead
import androidx.compose.material.icons.filled.MarkChatUnread
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText

@Composable
fun UnReadNotificationIcon() {
    Icon(
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        imageVector = Icons.Default.MarkChatUnread,
        contentDescription = UiText.StringResources(R.string.unread_icon_content_description).asString()
    )
}

@Composable
fun ReadNotificationIcon(expanded: Boolean) {
    Icon(
        imageVector = Icons.Default.MarkChatRead,
        tint = if (expanded) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = UiText.StringResources(R.string.read_icon_content_description).asString()
    )
}

@Composable
fun NotificationDocumentIcon(modifier: Modifier) {
    Icon(
        modifier = modifier,
        tint = MaterialTheme.colorScheme.secondary,
        imageVector = Icons.Default.Description,
        contentDescription = UiText.StringResources(R.string.document_content_description).asString()
    )
}