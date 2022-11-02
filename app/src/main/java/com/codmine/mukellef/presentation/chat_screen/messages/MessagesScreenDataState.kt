package com.codmine.mukellef.presentation.chat_screen.messages

import com.codmine.mukellef.domain.model.chat.Message
import com.codmine.mukellef.domain.util.UiText

data class MessagesScreenDataState(
    val userId: String = "",
    val userTitle: String = "",
    val receiverId: String = "",
    val receiverName: String = "",
    val receiverPlayerId: String = "",
    val message: String = "",
    val isLoading: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val messages: List<Message> = emptyList()
)
