package com.bsa.conna.view.fragment_factory

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bsa.conna.model.interfaces.OnProgressLoadingListener
import com.bsa.tars_android.model.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class ParentFragment
constructor(layoutRest: Int ) :
    Fragment(layoutRest) {

    @Inject
    lateinit var appUtils: AppUtils

    private lateinit var listener: OnProgressLoadingListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as OnProgressLoadingListener
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appUtils.loading.observe(viewLifecycleOwner, {
            if (it)
                listener.showProgress()
            else
                listener.hideProgress()

        })

    }


    override fun onDestroy() {
        super.onDestroy()
        listener.hideProgress()
    }

}