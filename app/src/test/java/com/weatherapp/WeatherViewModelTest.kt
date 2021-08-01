package com.weatherapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.weatherapp.data.api.ApiHelper
import com.weatherapp.data.model.WeatherData
import com.weatherapp.utils.Resource
import com.weatherapp.utils.TestCoroutineRule
import com.weatherapp.viewmodel.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class SingleNetworkCallViewModelTest {

    private val LATITUDE = "-41.211128"
    private val LONGITUDE = "174.908081"

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var apiHelper: ApiHelper

    @Mock
    private lateinit var apiUsersObserver: Observer<Resource<WeatherData>>


    private lateinit var weatherData: WeatherData

    @Before
    fun setUp() {
        weatherData = WeatherData(-41.211128, 174.908081, "", 0, arrayListOf())
    }

    @Test
    fun testSuccessResponse() {
        testCoroutineRule.runBlockingTest {
            doReturn(weatherData)
                .`when`(apiHelper)
                .getHourlyWeather(LATITUDE, LONGITUDE)
            val viewModel = WeatherViewModel(apiHelper, LATITUDE, LONGITUDE)
            viewModel.weatherDataObserver.observeForever(apiUsersObserver)
            verify(apiHelper).getHourlyWeather(LATITUDE, LONGITUDE)
            verify(apiUsersObserver).onChanged(Resource.success(weatherData))
            viewModel.weatherDataObserver.removeObserver(apiUsersObserver)
        }
    }

    @Test
    fun testFailure() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "Something Went Wrong"
            doThrow(RuntimeException(errorMessage))
                .`when`(apiHelper)
                .getHourlyWeather(LATITUDE, LONGITUDE)
            val viewModel = WeatherViewModel(apiHelper, LATITUDE, LONGITUDE)
            viewModel.weatherDataObserver.observeForever(apiUsersObserver)
            verify(apiHelper).getHourlyWeather(LATITUDE, LONGITUDE)
            verify(apiUsersObserver).onChanged(
                Resource.error(
                    RuntimeException(errorMessage).toString(),
                    null
                )
            )
            viewModel.weatherDataObserver.removeObserver(apiUsersObserver)
        }
    }

    @After
    fun tearDown() {
        // do something if required
    }

}
