package com.codmine.mukellef.domain.use_case.chat_screen

import com.codmine.mukellef.domain.repository.OnesignalRepository
import javax.inject.Inject

class SendPushNotification @Inject constructor(
    private val oneSignal: OnesignalRepository
) {
    operator fun invoke(receiverId: String, notification: String) {
        oneSignal.sendPushNotification(receiverId, notification)
    }
}