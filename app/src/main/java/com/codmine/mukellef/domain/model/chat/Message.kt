package com.codmine.mukellef.domain.model.chat

data class Message(
    val id: String,
    val content: String,
    val receiver: String,
    val sender: String,
    val status: Boolean,
    val postDate: String,
    val postTime: String
)
