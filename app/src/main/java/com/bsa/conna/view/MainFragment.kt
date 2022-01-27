package com.bsa.conna.view

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import com.bsa.conna.model.utils.ZoomOutPageTransformer
import com.bsa.conna.view.fragment_factory.ParentFragment
import com.bsa.conna.viewmodel.WeatherViewModel
import com.bsa.conna.viewmodel.WeatherViewState
import conna.R
import conna.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect

class MainFragment : ParentFragment(R.layout.fragment_main) {


    private val mBinding: FragmentMainBinding by viewBinding()
    private val viewModel: WeatherViewModel by hiltNavGraphViewModels(R.id.nav_graph)
    private val mWeatherAdapter: WeatherAdapter by lazy { WeatherAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpPagingAnimation()
        setUpFlowCollector()

        viewModel.getWeatherInfo()


    }


    private fun setUpFlowCollector() {

        lifecycleScope.launchWhenStarted {

            viewModel.mDocumentsFlow.collect {

                when (it) {

                    is WeatherViewState.SuccessState -> {
                        mWeatherAdapter.addWeatherItems(it.model.list)
                    }
                    is WeatherViewState.FailState -> {
                        appUtils.showSnack(requireActivity(), it.message)

                    }

                }


            }

        }


    }


    private fun setUpPagingAnimation() {

        mBinding.weatherRecy.adapter = mWeatherAdapter
        mBinding.weatherRecy.setPageTransformer(ZoomOutPageTransformer())


    }

}