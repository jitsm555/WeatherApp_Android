package com.weatherapp.data.api

import com.weatherapp.data.model.WeatherData

class ApiHelperImpl(private val apiService: WeatherApiService) : ApiHelper {

    override suspend fun getHourlyWeather(lat: String, lon: String): WeatherData {
        return apiService.getHourlyWeather(lat = lat, lon = lon)
    }
}