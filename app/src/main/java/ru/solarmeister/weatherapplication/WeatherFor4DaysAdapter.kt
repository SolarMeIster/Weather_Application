package ru.solarmeister.weatherapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.solarmeister.weatherapplication.databinding.ItemWeatherBinding

class WeatherFor4DaysAdapter : RecyclerView.Adapter<WeatherFor4DaysAdapter.ViewHolder>() {

    private lateinit var binding: ItemWeatherBinding

    class ViewHolder(binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemWeatherBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}