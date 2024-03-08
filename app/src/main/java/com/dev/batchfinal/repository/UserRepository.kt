package com.dev.batchfinal.repository

import com.dev.batchfinal.model.login_model.LoginResponseModel
import com.dev.batchfinal.network.ApiService
import com.dev.batchfinal.app_utils.NetworkErrorResult
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import javax.inject.Inject

class UserRepository @Inject constructor(private val apiService: ApiService) : BaseApiResponse() {

    suspend fun loginApi(jsonObject: JsonObject): Flow<NetworkErrorResult<LoginResponseModel>> {
        return flow {
            emit(safeApiCall { apiService.loginApi(jsonObject) })
        }.flowOn(Dispatchers.IO)
    }
}