package com.codmine.mukellef.data.remote.dto.chat

import com.codmine.mukellef.domain.model.chat.Message
import com.google.firebase.Timestamp
import java.text.DateFormat

data class MessageDto(
    val content: String = "",
    val receiver: String = "",
    val sender: String = "",
    val status: Boolean = false,
    val time: Timestamp = Timestamp.now()
)

fun MessageDto.toMessage(): Message {
    val postTime = time.toDate()
    val df = DateFormat.getDateInstance(DateFormat.SHORT)
    val tf = DateFormat.getTimeInstance(DateFormat.SHORT)
    return Message(
        id = "",
        content = content,
        receiver = receiver,
        sender = sender,
        status = false,
        postDate = df.format(postTime),
        postTime = tf.format(postTime)
    )
}