package com.weatherapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weatherapp.data.api.ApiHelper
import com.weatherapp.viewmodel.WeatherViewModel

class ViewModelFactory(
    private val apiHelperImpl: ApiHelper,
    private val lat: String,
    private val lon: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(apiHelperImpl, lat, lon) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}