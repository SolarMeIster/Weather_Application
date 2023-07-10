package ru.solarmeister.weatherapplication.model.current_weather

data class CurrentWeatherItem(val main: MainDataWeather, val weather: List<WeatherData>, val wind: WindDataWeather, val sys: SystemInfo)
