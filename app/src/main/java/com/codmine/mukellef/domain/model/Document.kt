package com.codmine.mukellef.domain.model

data class Document(
    val documentName: String,
    val postTime: String,
    val senderUser: String,
    val message: String,
    var readingTime: String
)
