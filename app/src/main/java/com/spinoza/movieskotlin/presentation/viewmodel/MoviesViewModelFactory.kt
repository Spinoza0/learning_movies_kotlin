package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.spinoza.movieskotlin.domain.MoviesApiService

class MoviesViewModelFactory(private val apiService: MoviesApiService) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass
            .getConstructor(MoviesApiService::class.java)
            .newInstance(apiService)
    }
}