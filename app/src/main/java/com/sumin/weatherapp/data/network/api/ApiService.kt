package com.sumin.weatherapp.data.network.api

import com.sumin.weatherapp.data.network.dto.CityDto
import com.sumin.weatherapp.data.network.dto.WeatherCurrentDto
import com.sumin.weatherapp.data.network.dto.WeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

import com.sumin.weatherapp.BuildConfig
/*
interface ApiService {

    @GET("current.json")
    suspend fun loadCurrentWeather(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto

    @GET("search.json")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>
}
*/
interface ApiService {

    @GET("current.json?key=${BuildConfig.WEATHER_API_KEY}")
    suspend fun loadCurrentWeather(
        @Query("q") query: String
    ): WeatherCurrentDto

    @GET("forecast.json?key=${BuildConfig.WEATHER_API_KEY}")
    suspend fun loadForecast(
        @Query("q") query: String,
        @Query("days") daysCount: Int = 4
    ): WeatherForecastDto

    @GET("search.json?key=${BuildConfig.WEATHER_API_KEY}")
    suspend fun searchCity(
        @Query("q") query: String
    ): List<CityDto>
}