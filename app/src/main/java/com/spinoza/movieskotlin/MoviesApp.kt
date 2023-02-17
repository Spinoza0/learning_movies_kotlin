package com.spinoza.movieskotlin

import android.app.Application
import com.spinoza.movieskotlin.di.dataModule
import com.spinoza.movieskotlin.di.domainModule
import com.spinoza.movieskotlin.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MoviesApp)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}