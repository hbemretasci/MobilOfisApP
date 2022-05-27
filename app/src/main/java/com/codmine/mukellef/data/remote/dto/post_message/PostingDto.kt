package com.codmine.mukellef.data.remote.dto.post_message

import com.google.gson.annotations.SerializedName

data class PostingDto(
    @SerializedName("GondermeSonucu")
    val postingResult: String,
    @SerializedName("Msg")
    val postingMessage: String
)