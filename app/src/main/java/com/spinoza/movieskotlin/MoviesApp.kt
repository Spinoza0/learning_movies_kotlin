package com.spinoza.movieskotlin

import android.app.Application
import com.spinoza.movieskotlin.di.dataModule
import com.spinoza.movieskotlin.di.domainModule
import com.spinoza.movieskotlin.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MoviesApp)
            modules(listOf(presentationModule, domainModule, dataModule))
        }
    }
}