package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.domain.model.validation.ValidationResult
import com.codmine.mukellef.domain.util.Constants.MAX_GIB_LENGTH
import com.codmine.mukellef.domain.util.UiText
import javax.inject.Inject

class ValidateGib @Inject constructor() {
    fun execute(gib: String): ValidationResult {
        if (gib.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(R.string.gib_blank)
            )
        }
        if (gib.length >  MAX_GIB_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(
                    R.string.max_gib_len_error,
                    MAX_GIB_LENGTH
                )
            )
        }
        return ValidationResult(
            successful = true,
            errorMessage = null
        )
    }
}