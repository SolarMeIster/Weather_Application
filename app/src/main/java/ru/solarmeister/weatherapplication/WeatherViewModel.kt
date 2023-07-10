package ru.solarmeister.weatherapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import ru.solarmeister.weatherapplication.model.current_weather.Weather
import java.text.SimpleDateFormat
import java.util.*

class WeatherViewModel : ViewModel() {

    private val _weather = MutableLiveData<Weather>()
    val weather: LiveData<Weather> = _weather

    /*fun getCurrentWeather(retrofitService: RetrofitService, lat: Double, lon: Double) {
        viewModelScope.run {
            val response = retrofitService.getCurrentWeather(
                lat.toString(),
                lon.toString()
            )
            response.enqueue(object : Callback<CurrentWeatherItem> {
                override fun onResponse(
                    call: Call<CurrentWeatherItem>,
                    response: Response<CurrentWeatherItem>
                ) {
                    response.body()
                    if (response.isSuccessful) {
                        _currentWeather.value = response.body()
                    }
                }

                override fun onFailure(call: Call<CurrentWeatherItem>, t: Throwable) {
                    Log.e("Connection weather", "Error of internet connection")
                }

            })
        }
    }*/

    fun parsingWeather(lat: Double, lon: Double) {
        try {
            viewModelScope.launch(Dispatchers.IO) {
                val document =
                    Jsoup.connect("https://yandex.ru/pogoda/?lat=$lat&lon=$lon").get()
                val currentTempElements = document.select("div.temp.fact__temp.fact__temp_size_s")
                val currentWindElement = document.select("span.wind-speed")
                val timesElements = document.select("div.fact__hour-label")
                val temperaturesElement = document.select("div.fact__hour-temp")
                val currentPressureElement = document.select("div.term.term_orient_v.fact__pressure")
                val currentHumidityElement = document.select("div.term.term_orient_v.fact__humidity")
                val conditionElement = document.select("div.link__condition.day-anchor.i-bem")
                _weather.postValue(Weather(
                    currentTempElements.text(),
                    getPressureOrHumidityFromHTML(currentPressureElement),
                    getPressureOrHumidityFromHTML(currentHumidityElement),
                    currentWindElement.text(),
                    conditionElement.textNodes()[0].text(),
                    timesElements.eachText(),
                    temperaturesElement.eachText()
                ))
            }
        } catch (e: java.lang.Exception) {
            Log.e("Error", "Error")
        }
    }

    private fun getPressureOrHumidityFromHTML(elements: Elements): String {
        val c = elements[0].children()
        for (element in c) {
            val cc = element.childNodes()
            val name = element.className()
            if (name == "term__value") {
                for (children in cc) {
                    if (children.nodeName() == "#text") {
                        return children.toString()
                    }

                }
            }
        }
        return ""
    }
}