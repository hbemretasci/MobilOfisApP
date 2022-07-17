package com.codmine.mukellef.domain.model.validation

import com.codmine.mukellef.domain.util.UiText

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: UiText? = null
)