package com.vaasudev.androidstructure.domain.repository

import com.vaasudev.androidstructure.domain.error_handle.NetworkError
import com.vaasudev.androidstructure.domain.response.InitResponse
import com.vaasudev.androidstructure.domain.utility.Result
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun init(url: String): Flow<Result<InitResponse, NetworkError>>
}