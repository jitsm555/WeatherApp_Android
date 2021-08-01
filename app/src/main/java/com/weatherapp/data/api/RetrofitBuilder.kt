package com.weatherapp.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

    private const val BASE_URL = "https://api.openweathermap.org/"
    var interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    var client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()


    val apiService: WeatherApiService = getRetrofit().create(WeatherApiService::class.java)

}