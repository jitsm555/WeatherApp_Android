package com.weatherapp.data.model
import com.google.gson.annotations.SerializedName

data class WeatherData (

	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double,
	@SerializedName("timezone") val timezone : String,
	@SerializedName("timezone_offset") val timezone_offset : Int,
	@SerializedName("hourly") val hourly : ArrayList<Hourly>
)