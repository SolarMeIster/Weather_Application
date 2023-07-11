package ru.solarmeister.weatherapplication

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.solarmeister.weatherapplication.databinding.FragmentWeatherBinding
import java.text.DateFormat
import java.util.*


class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private var coordinates = mutableListOf<Address>()
    private val weatherFor4DaysAdapter: WeatherFor4DaysAdapter by lazy {
        WeatherFor4DaysAdapter()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val geoCoder = Geocoder(requireContext(), Locale.getDefault())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geoCoder.getFromLocationName(
                "Zelenograd", 1
            ) { addresses -> coordinates = addresses }
        } else {
            coordinates = geoCoder.getFromLocationName("Zelenograd", 1) as MutableList<Address>
        }
        Log.i("Coordinates of city", "lat: ${coordinates[0].latitude}, lon: ${coordinates[0].longitude}")
        weatherViewModel.parsingWeather(
            coordinates[0].latitude,
            coordinates[0].longitude
        )
    }


    override fun onStart() {
        super.onStart()
        showCurrentWeather()
    }

    private fun setupRecyclerView() {
        with(binding) {
            recyclerView4Days.layoutManager = LinearLayoutManager(requireContext())
            recyclerView4Days.adapter = weatherFor4DaysAdapter
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showCurrentWeather() {
        weatherViewModel.weather.observe(viewLifecycleOwner) {
            weatherFor4DaysAdapter.weatherFor2DaysDataList = it.weatherFor2DaysList
            val currentTime =
                DateFormat.getTimeInstance(DateFormat.SHORT).format(Calendar.getInstance().time)
            if (it != null) {
                with(binding) {
                    temperature.text =
                        it.temp + getString(R.string.degree)
                    atmosphereValue.text =
                        it.pressure + getString(R.string.pascal)
                    windValue.text = it.wind + getString(R.string.velocity)
                    when (it.condition) {
                        getString(R.string.clear) -> {
                            if (currentTime > "18:00") {
                                weatherIcon.setImageResource(R.drawable.crescent_moon)
                                imageWeather.setImageResource(R.drawable.bright_night)
                            } else {
                                weatherIcon.setImageResource(R.drawable.sunny)
                                imageWeather.setImageResource(R.drawable.sunny_image)
                            }
                        }

                        getString(R.string.partly_cloudy) -> {
                            if (currentTime > "18:00") {
                                weatherIcon.setImageResource(R.drawable.night_cloud)
                                imageWeather.setImageResource(R.drawable.few_clouds_night_image)
                            } else {
                                weatherIcon.setImageResource(R.drawable.partly_cloudy)
                                imageWeather.setImageResource(R.drawable.few_clouds_image)
                            }
                        }

                        getString(R.string.cloudy) -> {
                            imageWeather.setImageResource(R.drawable.cloudy_image)
                            imageWeather.scaleX = 3.5f
                            imageWeather.scaleY = 3.5f
                            weatherIcon.setImageResource(R.drawable.cloud)
                        }

                        getString(R.string.overcast) -> {
                            imageWeather.setImageResource(R.drawable.cloudy_image)
                            imageWeather.scaleX = 3.5f
                            imageWeather.scaleY = 3.5f
                            weatherIcon.setImageResource(R.drawable.overcast)
                        }

                        getString(R.string.rain) -> {
                            weatherIcon.setImageResource(R.drawable.rainy)
                            imageWeather.setImageResource(R.drawable.raining_image)
                        }

                        getString(R.string.wet_snow) -> {
                            weatherIcon.setImageResource(R.drawable.freezing_rain)
                            imageWeather.setImageResource(R.drawable.raining_image)
                        }

                        getString(R.string.showers) -> {
                            weatherIcon.setImageResource(R.drawable.shower)
                            imageWeather.setImageResource(R.drawable.raining_image)
                        }

                        getString(R.string.light_rain) -> {
                            weatherIcon.setImageResource(R.drawable.light_rain)
                            imageWeather.setImageResource(R.drawable.raining_image)
                        }

                        getString(R.string.heavy_rain) -> {
                            weatherIcon.setImageResource(R.drawable.shower)
                            imageWeather.setImageResource(R.drawable.raining_image)
                        }

                        getString(R.string.thunderstorm) -> {
                            imageWeather.setImageResource(R.drawable.thunderstorm_image)
                            weatherIcon.setImageResource(R.drawable.thunderstorm)
                        }

                        getString(R.string.snow) -> {
                            windValue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            atmosphereValue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            temperature.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            imageWeather.setImageResource(R.drawable.snow_image)
                            atmosphereImage.setImageResource(R.drawable.atmospheric_night)
                            imageWind.setImageResource(R.drawable.wind_night)
                            weatherIcon.setImageResource(R.drawable.snowflake)
                        }

                        getString(R.string.hail) -> {
                            windValue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            atmosphereValue.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            temperature.setTextColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.black
                                )
                            )
                            imageWeather.setImageResource(R.drawable.hail_image)
                            atmosphereImage.setImageResource(R.drawable.atmospheric_night)
                            imageWind.setImageResource(R.drawable.wind_night)
                            weatherIcon.setImageResource(R.drawable.hail)
                        }
                    }
                    sunriseValue.text = it.sunrise
                    sunsetValue.text = it.sunset
                }
            }
        }
    }
}