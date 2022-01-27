package com.bsa.conna.viewmodel


import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bsa.conna.model.network_utils.remote.NetworkResponse
import com.bsa.conna.model.response.WeatherModel
import com.bsa.conna.repo.WeatherRepo
import com.bsa.tars_android.model.utils.AppUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repo: WeatherRepo,
    private val appUtils: AppUtils
) : ViewModel() {


    private val _mWeatherFlow: MutableStateFlow<WeatherViewState> by lazy {
        MutableStateFlow(
            WeatherViewState.EmptyState
        )
    }

    val mDocumentsFlow: MutableStateFlow<WeatherViewState> get() = _mWeatherFlow


    fun getWeatherInfo() {

        if (appUtils.loading.value == true)
            return


        viewModelScope.launch(IO) {

            appUtils.loading.postValue(true)

            val response =
                repo.getWeatherInfo()


            appUtils.loading.postValue(false)


            if (response is NetworkResponse.Success) {
                withContext(Default) {
                    repo.addWeatherInfo(response.body)
                }
                _mWeatherFlow.emit(WeatherViewState.SuccessState(response.body))
            } else {

                repo.getWeatherInfoFromDatabase().collect {
                    _mWeatherFlow.emit(WeatherViewState.SuccessState(it))
                }


            }

        }


    }


}


sealed class WeatherViewState {

    object EmptyState : WeatherViewState()
    data class SuccessState(val model: WeatherModel) : WeatherViewState()
    data class FailState(val message: String) : WeatherViewState()


}
