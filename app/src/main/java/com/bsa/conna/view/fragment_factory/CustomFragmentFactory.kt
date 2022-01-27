package com.bsa.conna.view.fragment_factory

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bsa.tars_android.model.utils.AppUtils
import javax.inject.Inject

class CustomFragmentFactory
@Inject constructor(
    private val appUtils: AppUtils ) : FragmentFactory() {


    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {


            else -> super.instantiate(classLoader, className)
        }
    }

}