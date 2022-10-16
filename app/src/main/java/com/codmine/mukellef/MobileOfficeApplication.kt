package com.codmine.mukellef

import android.app.Application
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp

const val ONESIGNAL_APP_ID = "66d6306b-13cd-400c-9e9d-51e5cd0ae0b3"

@HiltAndroidApp
class MobileOfficeApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
    }
}