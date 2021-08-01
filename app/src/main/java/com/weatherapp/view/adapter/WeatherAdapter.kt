package com.weatherapp.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.weatherapp.R
import com.weatherapp.data.model.Hourly
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter(
        private var hourlyWeatherList: ArrayList<Hourly>,
) : RecyclerView.Adapter<WeatherAdapter.ItemHolder>() {

    fun addData(hours: ArrayList<Hourly>) {
        hourlyWeatherList = hours
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val itemView =
                LayoutInflater.from(parent.context).inflate(R.layout.location_item, parent, false)
        return ItemHolder(itemView)
    }


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val hourData = hourlyWeatherList[position]
        holder.temp.text = hourData.temp.toString()
        holder.humidity.text = hourData.humidity.toString()
        holder.windSpeed.text = hourData.wind_speed.toString()
        val dayAndDate = Date(hourData.dt.toLong() * 1000)
        val dateFormat = SimpleDateFormat("hh:mm:ss").format(dayAndDate)
        holder.time.text = "Time: $dateFormat"
    }

    override fun getItemCount(): Int = hourlyWeatherList.size


    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        var temp: TextView = view.findViewById(R.id.temp)
        var humidity: TextView = view.findViewById(R.id.humidity)
        var windSpeed: TextView = view.findViewById(R.id.windSpeed)
        var time: TextView = view.findViewById(R.id.time)
    }
}

