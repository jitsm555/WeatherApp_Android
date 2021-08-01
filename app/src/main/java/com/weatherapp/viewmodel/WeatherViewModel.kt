package com.weatherapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.weatherapp.data.api.ApiHelper
import com.weatherapp.data.model.WeatherData
import com.weatherapp.data.repository.WeatherRepository
import com.weatherapp.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class WeatherViewModel(private val apiHelper: ApiHelper, private val lat: String, private val lon: String) : ViewModel() {
    private var weatherRepository: WeatherRepository? = null
    private val _weatherDataObserver = MutableLiveData<Resource<WeatherData>>()

    val weatherDataObserver = _weatherDataObserver

    init {
        weatherRepository = WeatherRepository(apiHelper)
        requestWeatherHourlyData()
    }

    private fun requestWeatherHourlyData() {
        viewModelScope.launch {
            _weatherDataObserver.postValue(Resource.loading(null))
            try {
                // coroutineScope is needed, else in case of any network error, it will crash
                coroutineScope {
                    val weatherDataDeferred = async { weatherRepository?.requestWeatherHourlyData(lat = lat, lon = lon) }
                    val weatherApi = weatherDataDeferred.await()
                    _weatherDataObserver.postValue(Resource.success(weatherApi))
                }

            } catch (e: Exception) {
                _weatherDataObserver.postValue(Resource.error("Something Went Wrong", null))
            }
        }

    }
}