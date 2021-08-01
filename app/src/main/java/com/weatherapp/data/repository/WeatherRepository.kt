package com.weatherapp.data.repository

import com.weatherapp.data.api.ApiHelper
import com.weatherapp.data.model.WeatherData

class WeatherRepository(private val apiHelper: ApiHelper) {

    suspend fun requestWeatherHourlyData(
            lat: String,
            lon: String): WeatherData {
        return apiHelper.getHourlyWeather(lat = lat, lon = lon)
    }

}

