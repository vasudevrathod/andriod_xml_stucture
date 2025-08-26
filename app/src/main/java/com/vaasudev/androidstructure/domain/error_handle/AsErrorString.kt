package com.vaasudev.androidstructure.domain.error_handle

import android.content.Context
import com.vaasudev.androidstructure.R
import java.net.UnknownHostException

fun Int.checkStatus(): NetworkError = when (this) {
    400 -> NetworkError.CLIENT_BAD_REQUEST
    401 -> NetworkError.CLIENT_UNAUTHORIZED
    403 -> NetworkError.CLIENT_FORBIDDEN
    404 -> NetworkError.CLIENT_NOT_FOUND
    else -> NetworkError.UNKNOWN
}

fun Throwable.toCustomExceptions(): NetworkError = when (this) {
    is retrofit2.HttpException ->
        when (this.code()) {
            400 -> NetworkError.CLIENT_BAD_REQUEST
            401 -> NetworkError.CLIENT_UNAUTHORIZED
            403 -> NetworkError.CLIENT_FORBIDDEN
            404 -> NetworkError.CLIENT_NOT_FOUND
            else -> NetworkError.UNKNOWN
        }

    is IllegalArgumentException -> NetworkError.ILLEGAL_ARGUMENT_EXCEPTION
    is IllegalStateException -> NetworkError.UNKNOWN
    is UnknownHostException -> NetworkError.NO_INTERNET_AVAILABLE
    is NullPointerException -> NetworkError.NULL_POINTER_EXCEPTION
    else -> NetworkError.UNKNOWN
}

fun NetworkError.asNetworkErrorString(context: Context): String = when (this) {
    NetworkError.SERVER_RESPONSE_EXCEPTION -> context.getString(R.string.server_response_error)
    NetworkError.CLIENT_BAD_REQUEST -> context.getString(R.string.bad_request)
    NetworkError.CLIENT_UNAUTHORIZED -> context.getString(R.string.unauthorized)
    NetworkError.CLIENT_FORBIDDEN -> context.getString(R.string.forbidden)
    NetworkError.CLIENT_NOT_FOUND -> context.getString(R.string.not_found)
    NetworkError.REDIRECT_RESPONSE_EXCEPTION -> context.getString(R.string.redirect_response_error)
    NetworkError.UNKNOWN -> context.getString(R.string.something_want_to_wrong)
    NetworkError.ILLEGAL_ARGUMENT_EXCEPTION -> context.getString(R.string.illegal_argument)
    NetworkError.NO_INTERNET_AVAILABLE -> context.getString(R.string.no_internet_available)
    NetworkError.NULL_POINTER_EXCEPTION -> context.getString(R.string.something_want_to_wrong)
}