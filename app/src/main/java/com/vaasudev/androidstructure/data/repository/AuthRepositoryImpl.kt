package com.vaasudev.androidstructure.data.repository

import android.content.Context
import com.vaasudev.androidstructure.data.remote.ApiCallInterface
import com.vaasudev.androidstructure.data.remote.commonApiCall
import com.vaasudev.androidstructure.domain.error_handle.NetworkError
import com.vaasudev.androidstructure.domain.error_handle.toCustomExceptions
import com.vaasudev.androidstructure.domain.repository.AuthRepository
import com.vaasudev.androidstructure.domain.response.InitResponse
import com.vaasudev.androidstructure.domain.utility.Result
import com.vaasudev.androidstructure.domain.utility.isConnected
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.net.UnknownHostException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val context: Context,
    private val api: ApiCallInterface,
): AuthRepository {
    override suspend fun init(url: String): Flow<Result<InitResponse, NetworkError>> = flow {
        try {
            if (!isConnected(context)) throw UnknownHostException()

            emit(commonApiCall(api.callInit(url = url)))
        } catch (e: Exception) {
            emit(Result.Error(e.toCustomExceptions()))
        }
    }.catch {
        emit(Result.Error(it.toCustomExceptions()))
    }
}