package com.bookitng.bookitng.networking

import com.bookitng.bookitng.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun<S> generateService(serviceClass: Class<S>) : S {

    val baseUrl = "https://bookitng.herokuapp.com/"

    val builder = GsonBuilder()
    builder.serializeNulls()
    builder.setDateFormat("yyyy-MM-dd")
    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(builder.create()))
        .baseUrl(baseUrl)

    val httpClientBuilder = OkHttpClient.Builder()
        .readTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClientBuilder.addInterceptor(loggingInterceptor)
    }

    retrofitBuilder.client(httpClientBuilder.build())
    return retrofitBuilder.build().create(serviceClass)
}