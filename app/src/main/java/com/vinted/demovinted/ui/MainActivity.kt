package com.vinted.demovinted.ui

import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.vinted.demovinted.base.BaseActivity
import com.vinted.demovinted.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(R.layout.activity_main) {

    private val navController: NavController by lazy {
        findNavController(R.id.main_nav_fragment)
    }

    override fun setupViews() {
        navController.setGraph(R.navigation.main_nav_graph)
    }
}