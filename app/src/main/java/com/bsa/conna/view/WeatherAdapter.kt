package com.bsa.conna.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bsa.conna.model.response.WeatherModel
import conna.databinding.WeatherItemBinding

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    private var mWeatherItems: ArrayList<WeatherModel.WeatherData>? = null


    fun addWeatherItems(data: ArrayList<WeatherModel.WeatherData>?) {
        mWeatherItems = data
        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder =
        WeatherViewHolder(
            WeatherItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent,
                false
            )
        )

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {

        holder.bindWeather(mWeatherItems!![position])

    }

    override fun getItemCount(): Int = mWeatherItems?.size ?: 0


    class WeatherViewHolder(private val view: WeatherItemBinding) :
        RecyclerView.ViewHolder(view.root) {


        fun bindWeather(model: WeatherModel.WeatherData) {

            view.model = model

        }


    }


}