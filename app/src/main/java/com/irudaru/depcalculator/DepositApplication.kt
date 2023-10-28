package com.irudaru.depcalculator

import android.app.Application
import com.irudaru.depcalculator.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 * Main application of app
 */
class DepositApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@DepositApplication)
            modules(appModule)
        }
    }
}