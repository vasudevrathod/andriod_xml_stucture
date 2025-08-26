package com.vaasudev.androidstructure.domain.utility

import com.vaasudev.androidstructure.domain.error_handle.Error
import org.json.JSONObject

typealias RootError = Error

sealed interface Result<out D, out E : RootError> {
    data class Success<out D, out E : RootError>(val data: D) : Result<D, E>
    data class Error<out D, out E : RootError>(val error: E) : Result<D, E>
}

sealed class Status {
    data object Loading : Status()
    data class Success<out D>(val data: D, val jsonObject: JSONObject = JSONObject()) : Status()
    data class Error<out E : RootError>(val error: E) : Status()
}