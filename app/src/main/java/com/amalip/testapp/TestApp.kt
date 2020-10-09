package com.amalip.testapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.amalip.testapp.application.di.*

class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TestApp)
            modules(
                listOf(
                    viewModelModules
                )
            )
        }
    }

}