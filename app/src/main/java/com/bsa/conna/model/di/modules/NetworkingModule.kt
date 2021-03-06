package com.bsa.conna.model.di.modules

import android.content.Context
import com.bsa.conna.model.network_utils.remote.NetworkResponseAdapterFactory
import com.bsa.conna.model.network_utils.APIServices
import com.bsa.conna.model.utils.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkingModule {




    @Singleton
    @Provides
    fun provideRetrofitClient(): OkHttpClient {


        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        return OkHttpClient().newBuilder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofitServices(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context
    ): APIServices =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory((NetworkResponseAdapterFactory(context)))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(okHttpClient)
            .build().create(APIServices::class.java)


}