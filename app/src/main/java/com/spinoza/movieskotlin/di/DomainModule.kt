package com.spinoza.movieskotlin.di

import com.spinoza.movieskotlin.data.repository.MoviesRepositoryImpl
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import org.koin.dsl.module

val domainModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            moviesApiService = get(),
            movieDao = get(),
            moviesMapper = get()
        )
    }
}