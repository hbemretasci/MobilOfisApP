package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.domain.repository.OnesignalRepository
import javax.inject.Inject

class SetOnesignalExternalId @Inject constructor(
    private val oneSignal: OnesignalRepository
) {
    operator fun invoke(id: String) {
        oneSignal.setExternalId(id)
    }
}