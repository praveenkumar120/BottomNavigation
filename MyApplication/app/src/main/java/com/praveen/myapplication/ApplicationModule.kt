package com.praveen.myapplication

import android.app.Application
import org.koin.core.context.startKoin

class ApplicationModule : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(viewModule,networkModule, repositoryModule))
        }
    }
}