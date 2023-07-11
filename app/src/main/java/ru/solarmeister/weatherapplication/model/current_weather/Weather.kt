package ru.solarmeister.weatherapplication.model.current_weather

data class Weather(
    val temp: String,
    val pressure: String,
    val humidity: String,
    val wind: String,
    val condition: String,
    val weatherFor2DaysList: List<WeatherFor2DaysData>,
    val sunrise: String,
    val sunset: String
    )

data class WeatherFor2Days(val weatherFor2DaysList: List<WeatherFor2DaysData>, val sunrise: String, val sunset: String)

data class WeatherFor2DaysData(val time: String, val temperature: String)
