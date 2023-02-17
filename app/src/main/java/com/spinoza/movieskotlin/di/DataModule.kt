package com.spinoza.movieskotlin.di

import com.spinoza.movieskotlin.data.database.MovieDao
import com.spinoza.movieskotlin.data.database.MovieDatabase
import com.spinoza.movieskotlin.data.network.MoviesApiFactory
import com.spinoza.movieskotlin.data.network.MoviesApiService
import org.koin.dsl.module

val dataModule = module {

    single<MovieDao> {
        MovieDatabase.getInstance(context = get()).movieDao()
    }

    single<MoviesApiService> {
        MoviesApiFactory.apiService
    }
}