package com.bsa.conna.model.network_utils


import com.bsa.conna.model.network_utils.remote.ErrorResponseBody
import com.bsa.conna.model.network_utils.remote.NetworkResponse
import com.bsa.conna.model.response.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query


typealias GenericResponse<S> = NetworkResponse<S, ErrorResponseBody>


private const val WEATHER = "forecast"
private const val API_KEY = "appid"


interface APIServices {


    @GET(WEATHER)
    suspend fun getWeatherData(
        @Query("q") city: String,
        @Query(API_KEY) key: String
    ): GenericResponse<WeatherModel>


}



