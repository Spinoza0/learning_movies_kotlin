package com.spinoza.movieskotlin

import androidx.lifecycle.LiveData
import com.spinoza.movieskotlin.movies.Movie

interface MoviesListShowable {
    fun getMovies(): LiveData<MutableList<Movie>>
    fun getIsLoading(): LiveData<Boolean>
    fun loadMovies()
}