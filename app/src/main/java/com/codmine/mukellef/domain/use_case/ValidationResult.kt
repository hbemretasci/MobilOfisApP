package com.codmine.mukellef.domain.use_case

import com.codmine.mukellef.domain.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)