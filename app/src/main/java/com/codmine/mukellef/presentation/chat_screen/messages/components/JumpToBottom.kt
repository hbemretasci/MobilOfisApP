package com.codmine.mukellef.presentation.chat_screen.messages.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codmine.mukellef.R
import com.codmine.mukellef.presentation.util.UiText
import com.codmine.mukellef.ui.theme.spacing

private enum class Visibility {
    VISIBLE,
    GONE
}

@Composable
fun JumpToBottom(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    // Show Jump to Bottom button

    val transition = updateTransition(if (enabled) Visibility.VISIBLE else Visibility.GONE,
        label = ""
    )

    val bottomOffset by transition.animateDp(label = "") {
        if (it == Visibility.GONE) {
            (-32).dp
        } else {
            32.dp
        }
    }

    if (bottomOffset > 0.dp) {
        ExtendedFloatingActionButton(
            icon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDownward,
                    modifier = Modifier.height(18.dp),
                    contentDescription = null
                )
            },
            text = {
                Text(text = UiText.StringResources(R.string.jumpBottom).asString())
            },
            onClick = onClicked,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = modifier
                .offset(x = 0.dp, y = -bottomOffset)
                .height(MaterialTheme.spacing.extraLarge)
        )
    }
}