package com.example.bottomanimationmydemo.factory

import com.example.bottomanimationmydemo.di.Http
import com.example.bottomanimationmydemo.di.PreLogin
import com.example.bottomanimationmydemo.model.RequestType
import com.example.bottomanimationmydemo.network.errorhandling.ErrorHandler
import javax.inject.Inject

class ErrorHandlerFactory @Inject constructor(
    @Http private val httpErrorHandler: ErrorHandler,
    @PreLogin private val preLoginErrorHandler: ErrorHandler
): IFactory<RequestType, ErrorHandler> {
    override fun create(data: RequestType): ErrorHandler {
        return when(data)
        {
            RequestType.POST_LOGIN -> {
                httpErrorHandler
            }
            RequestType.PRE_LOGIN -> {
                preLoginErrorHandler
            }
        }
    }
}