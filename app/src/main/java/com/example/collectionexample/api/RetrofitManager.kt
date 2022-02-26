package com.example.collectionexample.api

import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitManager {

    val httpLoggingInterceptor = HttpLoggingInterceptor()
    private val retrofit: Retrofit
    private val okHttpClient =
        OkHttpClient().newBuilder().readTimeout(500, TimeUnit.SECONDS)
            .connectTimeout(500, TimeUnit.SECONDS).writeTimeout(500, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .connectionPool(
                ConnectionPool(32, 5, TimeUnit.MINUTES)
            ).build()

     private val baseUrl = "https://api.opensea.io/api/v1/"




    init {

        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    companion object {
        private val manager = RetrofitManager()
        val client: Retrofit
            get() = manager.retrofit
    }
}