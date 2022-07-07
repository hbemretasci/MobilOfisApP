package com.codmine.mukellef.domain.util

import com.codmine.mukellef.presentation.util.UiText

sealed class Resource<T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(message: UiText, data: T? = null): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}