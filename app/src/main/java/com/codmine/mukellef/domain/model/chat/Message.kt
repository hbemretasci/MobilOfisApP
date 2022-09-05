package com.codmine.mukellef.domain.model.chat

import com.google.firebase.Timestamp

data class Message(
    val id: String,
    val content: String,
    val receiver: String,
    val sender: String,
    val time: Timestamp,
    val postDate: String,
    val postTime: String
)
