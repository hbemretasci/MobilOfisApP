package com.codmine.mukellef.domain.repository

interface OnesignalRepository {

    fun setExternalId(id: String)

    fun getPlayerId(): String

    fun sendPushNotification(targetId: String, notification: String)

}