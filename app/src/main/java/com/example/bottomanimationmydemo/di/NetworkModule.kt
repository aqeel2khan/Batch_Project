package com.example.bottomanimationmydemo.di

import com.example.bottomanimationmydemo.network.ApiService
import com.example.bottomanimationmydemo.network.AuthInterceptor
import com.example.bottomanimationmydemo.utils.MyConstant.BASE_URL
import com.google.gson.Gson
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
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(okHttp: OkHttpClient): ApiService {
        return Retrofit.Builder()
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
        if(BuildConfig.DEBUG) {
            builder.addInterceptor(okHttpProfilerInterceptor)
            builder.addInterceptor(httpLoggingInterceptor)
        }

        return builder.build()
    }
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
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