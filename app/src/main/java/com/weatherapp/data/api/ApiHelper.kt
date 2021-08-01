package com.weatherapp.data.api

import com.weatherapp.data.model.WeatherData

interface ApiHelper {

    suspend fun getHourlyWeather(lat: String, lon: String): WeatherData
}