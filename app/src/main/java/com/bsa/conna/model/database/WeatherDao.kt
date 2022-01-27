package com.bsa.conna.model.database

import androidx.room.*
import com.bsa.conna.model.response.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addWeatherInfo(document: WeatherModel)

    @Query("select * from weathermodel limit 1")
    fun getWeather(): Flow<WeatherModel>


}