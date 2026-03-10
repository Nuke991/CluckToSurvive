package com.mk.highfiler

import android.app.Application
import com.mk.highfiler.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HighFilerApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@HighFilerApplication)
            modules(appModules)
        }
    }
}