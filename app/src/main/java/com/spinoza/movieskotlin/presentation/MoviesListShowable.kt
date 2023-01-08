package com.spinoza.movieskotlin.presentation

import androidx.lifecycle.LiveData
import com.spinoza.movieskotlin.domain.movies.Movie

interface MoviesListShowable {
    fun getMovies(): LiveData<MutableList<Movie>>
    fun getIsLoading(): LiveData<Boolean>
    fun loadMovies()
}