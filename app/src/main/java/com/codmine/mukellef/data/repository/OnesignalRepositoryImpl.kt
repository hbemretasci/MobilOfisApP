package com.codmine.mukellef.data.repository

import com.codmine.mukellef.domain.repository.OnesignalRepository
import com.onesignal.OneSignal
import org.json.JSONException
import org.json.JSONObject

class OnesignalRepositoryImpl: OnesignalRepository {

    override fun setExternalId(id: String) {
        OneSignal.setExternalUserId(id)
    }

    override fun getPlayerId(): String {
        return OneSignal.getDeviceState()?.userId ?: "noplayerid"
    }


    override fun sendPushNotification(targetId: String, notification: String) {
        try {
            OneSignal.postNotification(
                JSONObject("{'contents': {'en':'$notification'}, 'include_player_ids': ['$targetId']}"),
                null
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}