package com.spinoza.movieskotlin.di

import com.spinoza.movieskotlin.data.repository.MoviesRepositoryImpl
import com.spinoza.movieskotlin.domain.repository.MoviesRepository
import com.spinoza.movieskotlin.domain.usecase.*
import org.koin.dsl.module

val domainModule = module {

    single<MoviesRepository> {
        MoviesRepositoryImpl(
            moviesApiService = get(),
            movieDao = get(),
            moviesMapper = get()
        )
    }

    factory<GetStateUseCase> {
        GetStateUseCase(moviesRepository = get())
    }

    factory<GetAllFavouritesMoviesUseCase> {
        GetAllFavouritesMoviesUseCase(moviesRepository = get())
    }

    factory<LoadOneMovieUseCase> {
        LoadOneMovieUseCase(moviesRepository = get())
    }

    factory<ChangeFavouriteStatusUseCase> {
        ChangeFavouriteStatusUseCase(moviesRepository = get())
    }

    factory<LoadMoviesUseCase> {
        LoadMoviesUseCase(moviesRepository = get())
    }

    factory<GetMoviesFromCacheUseCase> {
        GetMoviesFromCacheUseCase(moviesRepository = get())
    }
}