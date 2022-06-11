package com.codmine.mukellef.presentation.chat_screen.messages

import com.codmine.mukellef.domain.model.chat.Message

data class MessagesScreenDataState(
    val userId: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val messages: List<Message> = emptyList(),
    val error: String = ""
)
