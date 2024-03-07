package com.dev.batchfinal.factory

import com.dev.batchfinal.di.Http
import com.dev.batchfinal.di.PreLogin
import com.dev.batchfinal.model.RequestType
import com.dev.batchfinal.network.errorhandling.ErrorHandler
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