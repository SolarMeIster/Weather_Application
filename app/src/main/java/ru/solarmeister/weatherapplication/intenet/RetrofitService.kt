package ru.solarmeister.weatherapplication.intenet

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.solarmeister.weatherapplication.model.current_weather.CurrentWeatherItem

interface RetrofitService {

    @GET("data/2.5/weather?appid=9f4eb30c037b2515a7dbeb94bb6dc4d1")
    fun getCurrentWeather(@Query("lat") lat: String, @Query("lon") lon: String): Call<CurrentWeatherItem>

/*    @GET("geo/1.0/direct?q=London&appid=9f4eb30c037b2515a7dbeb94bb6dc4d1")
    fun getCoordinates(): Call<Coordinates>*/
}