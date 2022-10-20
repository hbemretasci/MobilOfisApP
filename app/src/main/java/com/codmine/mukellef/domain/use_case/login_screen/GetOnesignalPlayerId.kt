package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.OnesignalRepository
import javax.inject.Inject

class GetOnesignalPlayerId @Inject constructor(
    private val oneSignal: OnesignalRepository
) {
    operator fun invoke(): String {
        return oneSignal.getPlayerId()
    }
}