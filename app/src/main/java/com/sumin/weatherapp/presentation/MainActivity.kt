package com.sumin.weatherapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sumin.weatherapp.data.network.api.ApiFactory
import com.sumin.weatherapp.presentation.ui.theme.WeatherAppTheme
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


private const val TAG="MainActivity"
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Timber.tag(TAG)



        val apiService = ApiFactory.apiService

        CoroutineScope(Dispatchers.Main).launch {
            val currentWeather = apiService.loadCurrentWeather("London")
            val forecast = apiService.loadForecast("London")
            val cities = apiService.searchCity("London")
            Napier.d(
                "Current Weather 1: $currentWeather\nForecase Weather: $forecast\nCities:$cities"
            )

            Napier.d(tag = TAG) { "Current Weather 2: $currentWeather\nForecase Weather: $forecast\nCities:$cities" }
        }

        setContent {
            WeatherAppTheme {

            }
        }
    }
}