package ru.solarmeister.weatherapplication.model.current_weather

data class Weather(
    val temp: String,
    val pressure: String,
    val humidity: String,
    val wind: String,
    val condition: String,
    val listTimes: List<String>,
    val listTemp: List<String>
    )
