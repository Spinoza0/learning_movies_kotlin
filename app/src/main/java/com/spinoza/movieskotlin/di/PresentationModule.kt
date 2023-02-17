package com.spinoza.movieskotlin.di

import com.spinoza.movieskotlin.presentation.viewmodel.FavouriteMoviesViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MovieDetailViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel<MoviesViewModel> {
        MoviesViewModel(moviesRepository = get())
    }

    viewModel<FavouriteMoviesViewModel> {
        FavouriteMoviesViewModel(moviesRepository = get())
    }

    viewModel<MovieDetailViewModel> {
        MovieDetailViewModel(moviesRepository = get())
    }
}