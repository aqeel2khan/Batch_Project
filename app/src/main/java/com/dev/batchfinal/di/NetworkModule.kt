package com.dev.batchfinal.di

import com.dev.batchfinal.app_modules.account.network_service.AccountNetworkService
import com.dev.batchfinal.network.ApiService
import com.dev.batchfinal.network.AuthInterceptor
import com.dev.batchfinal.app_utils.MyConstant.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.itkacher.okprofiler.BuildConfig
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(AccountNetworkService.gson))
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build().create(ApiService::class.java)
    }
    @Singleton
    @Provides
    fun provideOkHttpClient(
        okHttpProfilerInterceptor: OkHttpProfilerInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(authInterceptor)

      //  if(BuildConfig.DEBUG) {
            builder.addInterceptor(okHttpProfilerInterceptor)
            builder.addInterceptor(httpLoggingInterceptor)
       // }

        return builder.build()
    }
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level =  HttpLoggingInterceptor.Level.BODY

          //  level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }
    @Singleton
    @Provides
    fun provideOkhttpProfiler(): OkHttpProfilerInterceptor {
        return OkHttpProfilerInterceptor()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }
}