package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.domain.MoviesApiService
import com.spinoza.movieskotlin.domain.MovieDao

class MovieDetailViewModelFactory(
    private val movieDao: MovieDao,
    private val kinopoiskApiService: MoviesApiService,
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(MovieDao::class.java, MoviesApiService::class.java)
            .newInstance(movieDao, kinopoiskApiService)
    }
}