package com.example.bottomanimationmydemo.repository

import com.example.bottomanimationmydemo.model.login_model.LoginResponseModel
import com.example.bottomanimationmydemo.network.ApiService
import com.example.bottomanimationmydemo.utils.NetworkErrorResult
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