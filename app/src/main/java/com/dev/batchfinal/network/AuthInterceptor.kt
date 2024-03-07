package com.dev.batchfinal.network

import com.dev.batchfinal.utils.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val sharedPreferences: AppSharedPreferences) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val mainRequest = chain.request()
        val builder = mainRequest.newBuilder()
            .method(mainRequest.method, mainRequest.body)

        if(sharedPreferences.token != null) {
            sharedPreferences.token?.let {
                builder.addHeader("Authorization", "Bearer $it")
            }
        }

        return chain.proceed(builder.build())
    }
}