package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.domain.MovieDao

class FavouriteMoviesViewModelFactory(private val movieDao: MovieDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(MovieDao::class.java)
            .newInstance(movieDao)
    }
}