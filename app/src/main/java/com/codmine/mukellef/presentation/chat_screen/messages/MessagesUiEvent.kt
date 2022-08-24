package com.codmine.mukellef.presentation.chat_screen.messages

import com.codmine.mukellef.domain.util.UiText

sealed class MessagesUiEvent {
    data class ShowSnackbar(val message: UiText): MessagesUiEvent()
    object SendMessageSuccess: MessagesUiEvent()
}
