package com.bsa.conna.model.database

import androidx.room.TypeConverter
import com.bsa.conna.model.response.WeatherModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.ArrayList

class WeatherDatabaseConverter {


    @TypeConverter
    fun fromDocumentFiles(countryLang: ArrayList<WeatherModel.WeatherData?>?): String? {
        if (countryLang == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<WeatherModel.WeatherData?>?>() {}.type
        return gson.toJson(countryLang, type)
    }

    @TypeConverter
    fun toDocumentFiles(countryLangString: String?): ArrayList<WeatherModel.WeatherData?>? {
        if (countryLangString == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<ArrayList<WeatherModel.WeatherData?>?>() {}.type
        return gson.fromJson<ArrayList<WeatherModel.WeatherData?>>(countryLangString, type)
    }




  

}