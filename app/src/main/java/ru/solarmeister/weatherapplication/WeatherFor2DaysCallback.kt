package ru.solarmeister.weatherapplication

import androidx.recyclerview.widget.DiffUtil
import ru.solarmeister.weatherapplication.model.current_weather.WeatherFor2DaysData

class WeatherFor2DaysCallback(private val oldList: List<WeatherFor2DaysData>, private val newList: List<WeatherFor2DaysData>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].time === newList[newItemPosition].temperature
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val (oldTime, oldTemp) = oldList[oldItemPosition]
        val (newTime, newTemp) = newList[newItemPosition]
        return oldTemp == newTemp && oldTime == newTime
    }
}