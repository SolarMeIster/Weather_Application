package ru.solarmeister.weatherapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.solarmeister.weatherapplication.databinding.ItemWeatherBinding
import ru.solarmeister.weatherapplication.model.current_weather.WeatherFor2DaysData

class WeatherFor4DaysAdapter : RecyclerView.Adapter<WeatherFor4DaysAdapter.ViewHolder>() {

    private lateinit var binding: ItemWeatherBinding

    var weatherFor2DaysDataList = emptyList<WeatherFor2DaysData>()
        set(newValue) {
            val diffCallback = WeatherFor2DaysCallback(field, newValue)
            val diffWeatherFor2Days = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffWeatherFor2Days.dispatchUpdatesTo(this)
        }

    class ViewHolder(private val binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(weatherFor2DaysData: WeatherFor2DaysData) {
            with(binding) {
                time.text = weatherFor2DaysData.time
                temp.text = weatherFor2DaysData.temperature
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherFor2DaysData = weatherFor2DaysDataList[position]
        holder.bind(weatherFor2DaysData)
    }

    override fun getItemCount(): Int = weatherFor2DaysDataList.size

}