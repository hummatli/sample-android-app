package com.vinted.demovinted

import android.app.Application
import com.vinted.demovinted.utils.TimberInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject lateinit var timberInitializer: TimberInitializer

    override fun onCreate() {
        super.onCreate()
        timberInitializer.init(this)
    }
}