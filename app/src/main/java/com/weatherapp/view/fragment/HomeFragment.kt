package com.weatherapp.view.fragment

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.weatherapp.R
import com.weatherapp.data.api.ApiHelperImpl
import com.weatherapp.data.api.RetrofitBuilder
import com.weatherapp.data.model.Hourly
import com.weatherapp.utils.Status
import com.weatherapp.utils.ViewModelFactory
import com.weatherapp.view.adapter.WeatherAdapter
import com.weatherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private lateinit var mAdapter: WeatherAdapter

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mViewModel: WeatherViewModel
    private lateinit var mHours: ArrayList<Hourly>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAdapter = WeatherAdapter(arrayListOf())
        recyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(activity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        obtainLocation()
    }


    @SuppressLint("MissingPermission")
    private fun obtainLocation() {
        // get the last location
        fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location ->
                    setupViewModel(location.latitude.toString(), location.longitude.toString())
                    setupObserver()
                }
    }

    private fun setupViewModel(lat: String, lon: String) {
        mViewModel = ViewModelProviders.of(
                requireActivity(),
                ViewModelFactory(
                        ApiHelperImpl(RetrofitBuilder.apiService), lat, lon)
        ).get(WeatherViewModel::class.java)
    }

    private fun setupObserver() {
        mViewModel.weatherDataObserver.observe(requireActivity(), {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { weatherData -> renderList(weatherData.hourly) }
                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(list: ArrayList<Hourly>) {
        mHours = list
        mAdapter.addData(mHours)
        mAdapter.notifyDataSetChanged()
    }
}