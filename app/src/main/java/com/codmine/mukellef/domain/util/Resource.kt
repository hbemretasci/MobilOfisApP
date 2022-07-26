package com.codmine.mukellef.domain.util

sealed class Resource<T>(val data: T? = null, val message: UiText? = null) {
    class Success<T>(data: T): Resource<T>(data)
    class Error<T>(data: T? = null, message: UiText): Resource<T>(data, message)
    class Loading<T>(data: T? = null): Resource<T>(data)
}