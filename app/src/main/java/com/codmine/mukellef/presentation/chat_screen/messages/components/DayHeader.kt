package com.codmine.mukellef.presentation.chat_screen.messages.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codmine.mukellef.ui.theme.spacing

@Composable
fun DayHeader(postDate: String) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.small
            )
            .height(MaterialTheme.spacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DayHeaderLine()
        Text(
            text = postDate,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(.4f)
        )
        DayHeaderLine()
    }
}

@Composable
private fun RowScope.DayHeaderLine() {
    Divider(
        modifier = Modifier.weight(1f),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
    )
}