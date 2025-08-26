package com.vaasudev.androidstructure.domain.error_handle

sealed interface Error


enum class NetworkError : Error {
    SERVER_RESPONSE_EXCEPTION,
    CLIENT_BAD_REQUEST,
    CLIENT_UNAUTHORIZED,
    CLIENT_FORBIDDEN,
    CLIENT_NOT_FOUND,
    REDIRECT_RESPONSE_EXCEPTION,
    ILLEGAL_ARGUMENT_EXCEPTION,
    NO_INTERNET_AVAILABLE,
    NULL_POINTER_EXCEPTION,
    UNKNOWN
}