package com.codmine.mukellef.domain.use_case.login_screen

import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.ValidationResult
import com.codmine.mukellef.domain.util.Constants.MAX_PASSWORD_LENGTH
import com.codmine.mukellef.presentation.util.UiText
import javax.inject.Inject

class ValidatePassword @Inject constructor() {

    fun execute(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(R.string.password_blank)
            )
        }
        if (password.length >  MAX_PASSWORD_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(
                    R.string.max_password_len_error,
                    MAX_PASSWORD_LENGTH
                )
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}