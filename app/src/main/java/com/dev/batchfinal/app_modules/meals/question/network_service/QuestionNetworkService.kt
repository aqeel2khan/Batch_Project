package com.dev.batchfinal.app_modules.question.network_service

import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.account.model.UpdateProfileModel
import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoalsResponse
import com.dev.batchfinal.app_utils.MyConstant.BASE_URL
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
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.HeaderMap
import retrofit2.http.POST
import retrofit2.http.PUT
import java.util.concurrent.TimeUnit

interface QuestionNetworkService {

    @GET("meal/goals")
    fun requestMealGoals(@Body request: RequestBody): retrofit2.Call<MealGoalsResponse>

    companion object {
        var mealGoals = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        fun create(): QuestionNetworkService {
            var mService: QuestionNetworkService? = null
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS).addInterceptor(mealGoals)
                .build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            mService = retrofit.create(QuestionNetworkService::class.java)
            return mService
        }
    }
}