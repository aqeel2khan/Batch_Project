package com.dev.batchfinal.app_modules.account.network_service

import com.dev.batchfinal.app_modules.account.model.DeliveryAddressModel
import com.dev.batchfinal.app_modules.account.model.GetDelivaryAddressModel
import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.account.model.SignUpModel
import com.dev.batchfinal.app_modules.account.model.UpdateProfileImg
import com.dev.batchfinal.app_modules.account.model.UpdateProfileModel
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

interface AccountNetworkService {

    @POST("auth/signin")     //Login + Token
    fun requestLogin(@Body request: RequestBody): retrofit2.Call<SignInModel>

    @PUT("account/profile")     //Login + Token
    fun requestUpdateProfile(@HeaderMap headersMap: Map<String, String>, @Body request: RequestBody): retrofit2.Call<UpdateProfileModel>


    @POST("account/address")
    fun requestAddDeliveryAddress(@HeaderMap headersMap: Map<String, String>, @Body request: RequestBody): retrofit2.Call<DeliveryAddressModel>

    @GET("account/address")
    fun requestGetDeliveryAddress(@HeaderMap headersMap: Map<String, String>,): retrofit2.Call<GetDelivaryAddressModel>

    @POST("auth/signup")
    fun requestSignUp(@Body params: Map<String, String>): retrofit2.Call<SignUpModel>

    @POST("account/profileimage")
    fun requestProfileImgUpdate(@HeaderMap headersMap: Map<String, String>,@Body request: RequestBody): retrofit2.Call<UpdateProfileImg>

    companion object {
        var logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        var gson: Gson = GsonBuilder()
            .setLenient()
            .create()
        fun create(): AccountNetworkService {
            var mService: AccountNetworkService? = null
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
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .build()
            mService = retrofit.create(AccountNetworkService::class.java)
            return mService
        }


    }



}