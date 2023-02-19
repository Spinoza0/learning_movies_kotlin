package com.spinoza.movieskotlin.di

import com.spinoza.movieskotlin.presentation.viewmodel.FavouriteMoviesViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MovieDetailViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MoviesViewModel> {
        MoviesViewModel(
            getStateUseCase = get(),
            resetStateUseCase = get(),
            loadAllMoviesUseCase = get(),
            loadMovieDetailsUseCase = get()
        )
    }

    viewModel<FavouriteMoviesViewModel> {
        FavouriteMoviesViewModel(
            getStateUseCase = get(),
            resetStateUseCase = get(),
            getAllFavouritesMoviesUseCase = get(),
            loadMovieDetailsUseCase = get()
        )
    }

    viewModel<MovieDetailViewModel> {
        MovieDetailViewModel(
            getStateUseCase = get(),
            changeFavouriteStatusUseCase = get()
        )
    }
}