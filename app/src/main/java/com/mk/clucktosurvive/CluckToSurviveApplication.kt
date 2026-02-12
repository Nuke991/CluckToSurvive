package com.mk.clucktosurvive

import android.app.Application
import di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CluckToSurviveApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CluckToSurviveApplication)
            modules(appModules)
        }
    }
}