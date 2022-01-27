package com.bsa.conna.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.bsa.conna.model.interfaces.OnProgressLoadingListener
import conna.R
import conna.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnProgressLoadingListener {

    private val mBinding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val navHost = supportFragmentManager.findFragmentById(R.id.nav_id) as NavHostFragment
        navController = navHost.findNavController()

        setSupportActionBar(mBinding.toolbar)
        setupActionBarWithNavController(navController)




    }




    override fun showProgress() {
        mBinding.waitingProgress.isVisible = true
    }

    override fun hideProgress() {
        mBinding.waitingProgress.isVisible = false
    }
}