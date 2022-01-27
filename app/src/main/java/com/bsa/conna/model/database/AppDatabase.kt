package com.bsa.conna.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bsa.conna.model.response.WeatherModel


@Database(
    entities = [WeatherModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(WeatherDatabaseConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun documentDao(): WeatherDao
}