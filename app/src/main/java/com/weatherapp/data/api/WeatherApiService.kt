package com.weatherapp.data.api

import com.weatherapp.data.model.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {
    // https://api.openweathermap.org/data/2.5/onecall?lat=-41.211128&lon=174.908081&exclude=daily,minutely,current,alert&appid=fae7190d7e6433ec3a45285ffcf55c86
    @GET("data/2.5/onecall?")
    suspend fun getHourlyWeather(@Query("lat") lat: String,
                                 @Query("lon") lon: String,
                                 @Query("exclude") exclude: String = "daily,minutely,current,alert",
                                 @Query("appid") appid: String = "fae7190d7e6433ec3a45285ffcf55c86"): WeatherData

}