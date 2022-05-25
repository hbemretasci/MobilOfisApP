package com.codmine.mukellef.domain.use_case.login_screen

import android.content.Context
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.ValidationResult
import com.codmine.mukellef.domain.util.Constants.MAX_GIB_LENGTH
import com.codmine.mukellef.domain.util.Constants.MAX_VK_LENGTH
import com.codmine.mukellef.presentation.util.UiText
import javax.inject.Inject

class ValidateVk @Inject constructor() {

    fun execute(vk: String, context: Context): ValidationResult {
        if (vk.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(R.string.vk_blank).asString(context)
            )
        }
        if (vk.length >  MAX_VK_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(
                    R.string.max_vk_len_error,
                    MAX_VK_LENGTH
                ).asString(context)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}