package com.bsa.conna.repo

import com.bsa.conna.model.database.WeatherDao
import com.bsa.conna.model.network_utils.APIServices
import com.bsa.conna.model.response.WeatherModel
import com.bsa.conna.model.utils.API_KEY
import com.bsa.conna.model.utils.CITY_NAME
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WeatherRepo @Inject constructor(
    private val api: APIServices,
    private val dao: WeatherDao
) {


    suspend fun getWeatherInfo() = api.getWeatherData(CITY_NAME, API_KEY)

    suspend fun addWeatherInfo(weather: WeatherModel) = dao.addWeatherInfo(weather)

    suspend fun getWeatherInfoFromDatabase() = dao.getWeather()


}