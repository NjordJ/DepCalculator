package com.irudaru.depcalculator

import android.app.Application
import com.irudaru.depcalculator.di.injectFeature
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Main application of app
 */
class DepositApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger() // if (BuildConfig) Level.ERROR else Level.NONE
            androidContext(this@DepositApplication)
            injectFeature()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}