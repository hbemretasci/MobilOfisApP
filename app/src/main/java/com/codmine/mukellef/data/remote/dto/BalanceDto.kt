package com.codmine.mukellef.data.remote.dto

import com.google.gson.annotations.SerializedName

data class BalanceDto(
    @SerializedName("bildirimler")
    val transactions: List<TransactionDto>,
    @SerializedName("islemzamani")
    val processingTime: String
)
