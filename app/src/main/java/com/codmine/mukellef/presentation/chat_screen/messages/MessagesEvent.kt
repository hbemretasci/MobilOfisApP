package com.codmine.mukellef.presentation.chat_screen.messages

sealed class MessagesEvent {
    object AddMessagesListener: MessagesEvent()
    object RemoveMessagesListener: MessagesEvent()
    data class PostReadingMessage(val messageId: String): MessagesEvent()
    data class MessageChanged(val messageValue: String): MessagesEvent()
    data class PostMessage(val message: String): MessagesEvent()
}
