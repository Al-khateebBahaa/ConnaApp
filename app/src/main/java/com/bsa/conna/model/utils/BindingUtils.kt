package com.bsa.conna.model.utils

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import com.bsa.conna.model.response.WeatherModel
import conna.R


object BindingUtils {


    @JvmStatic
    @BindingAdapter("addDate", "isTime")
    fun splitText(view: TextView, date: String?, isTime: Boolean) {

        if (date.isNullOrBlank()) {
            view.text = ""
            return
        }

        try {

            if (!isTime) {
                view.text = date.split(" ")[0]
            } else {
                view.text = date.split(" ")[1]
            }

        } catch (e: IndexOutOfBoundsException) {

            view.text = date
        }


    }


    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImage(view: ImageView, imageName: List<WeatherModel.WeatherData.Weather>?) {

        if (imageName.isNullOrEmpty()) return


        view.load("http://openweathermap.org/img/wn/${imageName[0].icon}@2x.png") {
            placeholder(R.drawable.ic_launcher_background)

        }





    }
}