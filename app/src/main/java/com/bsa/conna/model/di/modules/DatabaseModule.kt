package com.bsa.conna.model.di.modules

import androidx.room.Room
import com.bsa.conna.model.database.AppDatabase
import com.bsa.conna.model.database.WeatherDao
import com.bsa.conna.model.di.AppApplication


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(baseApp: AppApplication): AppDatabase =
        Room.databaseBuilder(baseApp, AppDatabase::class.java, "v1_db")
            .fallbackToDestructiveMigration()
            .build()



    @Singleton
    @Provides
    fun provideDocumentOfflineDao(database: AppDatabase): WeatherDao = database.documentDao()


}