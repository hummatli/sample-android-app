package com.vinted.demovinted.utils

import android.app.Application
import com.vinted.demovinted.BuildConfig
import timber.log.Timber

class TimberInitializer {
    fun init(application: Application) {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                // Adds the line number to the tag
                override fun createStackElementTag(element: StackTraceElement) =
                    "${super.createStackElementTag(element)}:${element.lineNumber}"
            })
        }
    }
}