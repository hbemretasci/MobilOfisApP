package com.codmine.mukellef.presentation.notification_screen.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.MarkChatRead
import androidx.compose.material.icons.filled.MarkChatUnread
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.codmine.mukellef.R
import com.codmine.mukellef.presentation.util.UiText

@Composable
fun UnReadNotificationIcon() {
    Icon(
        tint = MaterialTheme.colorScheme.onPrimaryContainer,
        imageVector = Icons.Default.MarkChatUnread,
        contentDescription = UiText.StringResources(R.string.document_unread_icon_content_description).asString()
    )
}

@Composable
fun ReadNotificationIcon(expanded: Boolean) {
    Icon(
        imageVector = Icons.Default.MarkChatRead,
        tint = if (expanded) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimaryContainer,
        contentDescription = UiText.StringResources(R.string.document_read_icon_content_description).asString()
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