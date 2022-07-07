package com.codmine.mukellef.presentation.chat_screen.messages

import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.presentation.util.UiText

data class MessagesScreenDataState(
    val userId: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val messages: List<Message> = emptyList()
)
