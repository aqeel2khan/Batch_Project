package com.example.bottomanimationmydemo.network.errorhandling

import com.example.bottomanimationmydemo.model.response.SampleOtherErrorResponse
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class PreLoginErrorHandler @Inject constructor(
    private val gson: Gson
): ErrorHandler {
    override fun getErrorMessageFrom(ex: Exception): String? {
        if(ex is HttpException) {
            ex.response()?.errorBody()?.let { responseBody ->
                return when(ex.code()) {
                    400 -> {
                        val badRequestError = gson.fromJson(responseBody.string(), SampleOtherErrorResponse::class.java)
                        badRequestError.otherMessage
                    } else -> {
                        null
                    }
                }
            }
        } else {
            return null
        }

        return null
    }
}