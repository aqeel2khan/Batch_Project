package com.dev.batchfinal.app_modules.scanning.network_service

import com.dev.batchfinal.app_modules.scanning.model.CourseOrderListModel
import com.dev.batchfinal.app_modules.scanning.model.MealSubscriptionDetailsResponse
import com.dev.batchfinal.app_utils.MyConstant
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface ScanningNetworkService {

    @PUT("subscription/list")
    fun requestMealSubsCription(@HeaderMap headersMap: Map<String, String>, @Body request: RequestBody): retrofit2.Call<MealSubscriptionDetailsResponse>

    @PUT("course/order/list")
    fun requestCourseOrder(@HeaderMap headersMap: Map<String, String>, @Body request: RequestBody): retrofit2.Call<CourseOrderListModel>

    companion object {
        var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        fun create(): ScanningNetworkService {
            var mService: ScanningNetworkService? = null
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
            mService = retrofit.create(ScanningNetworkService::class.java)
            return mService
        }


    }

}