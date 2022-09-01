package com.codmine.mukellef.presentation.chat_screen.messages.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.util.UiText
import com.codmine.mukellef.ui.theme.spacing
import com.google.accompanist.insets.navigationBarsWithImePadding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInput(
    text: String,
    modifier: Modifier,
    focusRequester: FocusRequester,
    onValueChange: (String) -> Unit,
    sendMessage: (String) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
        ) {
            OutlinedTextField(
                value = text,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsWithImePadding()
                    .focusRequester(focusRequester),
                onValueChange = onValueChange,
                singleLine = false,
                maxLines = 3,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions { sendMessage(text) },
                placeholder = { Text(UiText.StringResources(R.string.send_message_place_holder).asString()) },
                trailingIcon = {
                    IconButton(
                        enabled = text.isNotBlank(),
                        onClick = {sendMessage(text) }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            tint = if(text.isNotBlank()) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            contentDescription = UiText.StringResources(
                                R.string.send_message_content_description
                            ).asString()
                        )
                    }
                }
            )
        }
    }
}