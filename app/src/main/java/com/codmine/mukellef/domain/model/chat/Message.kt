package com.codmine.mukellef.domain.model.chat

data class Message(
    val receivedUserId: String,
    val postTime: String,
    val senderUser: String,
    val senderUserId: String,
    val readingTime: String,
    val message: String,
    val id: String
)
