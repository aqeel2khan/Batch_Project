package com.dev.batchfinal.app_modules.notifications.network_service

import com.dev.batchfinal.app_modules.notifications.model.NotificationsModel
import com.dev.batchfinal.app_utils.MyConstant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import java.util.concurrent.TimeUnit

interface NotificationNetworkService {

    @GET("account/notification")
    fun requestNotification(@HeaderMap headersMap: Map<String, String>,): retrofit2.Call<NotificationsModel>


    companion object {
        var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        fun create(): NotificationNetworkService {
            var mService: NotificationNetworkService? = null
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).addInterceptor(logging)
                .build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(MyConstant.BASE_URL)
                .client(okHttpClient)
                .build()
            mService = retrofit.create(NotificationNetworkService::class.java)
            return mService
        }


    }

}