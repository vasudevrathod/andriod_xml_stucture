package com.vaasudev.androidstructure.data.remote

import com.google.gson.JsonParser
import com.vaasudev.androidstructure.domain.error_handle.NetworkError
import com.vaasudev.androidstructure.domain.error_handle.checkStatus
import com.vaasudev.androidstructure.domain.utility.printLog
import com.vaasudev.androidstructure.domain.utility.Result
import retrofit2.Response

fun <T> commonApiCall(body: Response<T>): Result<T, NetworkError>  {
    if (body.isSuccessful) {
        printLog("Test", "Success ${body.message()}")
        body.body()?.let { bodyData ->
            return Result.Success(bodyData)
        }
    } else {
        printLog("Test", "Failure ${body.message()}")
        printLog("Test", "Failure ${body.code()}")
        printLog("Test", "Failure ${body.body().toString()}")
        printLog("Test", "Failure ${body.raw()}")
        body.errorBody()?.let { data ->
            val errorJsonString = data.string()
            val message = JsonParser().parse(errorJsonString)
                .asJsonObject["error"]
                .asString
            printLog("Test", "Failure $message")
        }
        return Result.Error(body.code().checkStatus())
    }
    return Result.Error(NetworkError.UNKNOWN)
}