package com.pezesha.agent.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pezesha.agent.BuildConfig
import com.pezesha.agent.data.remote.interfaces.AuthApi
import com.pezesha.agent.data.remote.utils.NetInterceptor
import com.pezesha.agent.utils.GlobalMethods
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Singleton
    @Provides
    fun providesNetInterceptor(globalMethods: GlobalMethods): NetInterceptor {
        return NetInterceptor(globalMethods)
    }


    @Singleton
    @Provides
    fun providesHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
//        netInterceptor: NetInterceptor
    ): OkHttpClient {
        val okHttp = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(netInterceptor)
            .addInterceptor(loggingInterceptor)
        return okHttp.build()
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesRetrofit(gson: Gson, client: OkHttpClient): Retrofit {
        val baseUrl = BuildConfig.URL_SCHEME + "example.com"
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(baseUrl)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesAuthenticationAPI(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

}
