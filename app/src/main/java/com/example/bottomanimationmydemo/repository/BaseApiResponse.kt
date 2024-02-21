package com.example.bottomanimationmydemo.repository

import com.example.bottomanimationmydemo.utils.NetworkErrorResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException

abstract class BaseApiResponse() {
    suspend fun <T> safeApiCall(apiCall: suspend () -> T): NetworkErrorResult<T> {
        return withContext(Dispatchers.IO) {
            try {
                NetworkErrorResult.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val jObjError = JSONObject(throwable.response()!!.errorBody()!!.string())

                        NetworkErrorResult.Error("${jObjError}")
                    }
                    else -> {
                        NetworkErrorResult.Error("")
                    }
                }
            }
        }
    }
    private fun <T> error2(s: String): NetworkErrorResult<T>  = NetworkErrorResult.Error(s.toString())
}