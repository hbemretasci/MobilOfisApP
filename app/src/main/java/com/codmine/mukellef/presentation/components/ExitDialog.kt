package com.codmine.mukellef.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText

@Composable
fun ExitDialog(
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* openExitAppDialog.value = false */ },
        text = {
            Text(
                text = UiText.StringResources(R.string.exit_dialog_message).asString(),
                fontWeight = FontWeight.Bold
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmClick
            ) {
                Text(
                    UiText.StringResources(R.string.exit_dialog_confirm_message).asString()
                )
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissClick
            ) {
                Text(
                    UiText.StringResources(R.string.exit_dialog_cancel_message).asString()
                )
            }
        }
    )
}