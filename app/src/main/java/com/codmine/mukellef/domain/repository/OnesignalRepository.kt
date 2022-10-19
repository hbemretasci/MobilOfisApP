package com.codmine.mukellef.domain.repository

interface OnesignalRepository {

    fun setExternalId(id: String)

    fun sendPushNotification(receiverId: String, notification: String)

}