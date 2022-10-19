package com.codmine.mukellef.data.repository

import com.codmine.mukellef.domain.repository.OnesignalRepository
import com.onesignal.OneSignal
import org.json.JSONException

import org.json.JSONObject

class OnesignalRepositoryImpl: OnesignalRepository {

    override fun setExternalId(id: String) {
        OneSignal.setExternalUserId(id)
    }

    override fun sendPushNotification(receiverId: String, notification: String) {
        try {
            OneSignal.postNotification(
                JSONObject("{'contents': {'en':'$notification'}, 'include_external_user_ids': ['$receiverId']}"),
                null
            )
        } catch (e: JSONException) {
            //e.printStackTrace()
        }
    }

}