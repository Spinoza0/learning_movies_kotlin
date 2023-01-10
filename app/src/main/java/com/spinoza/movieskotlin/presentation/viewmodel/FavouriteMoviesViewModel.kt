package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.movieskotlin.domain.MovieDao
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.presentation.MoviesListShowable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FavouriteMoviesViewModel(private val movieDao: MovieDao) :
    ViewModel(),
    MoviesListShowable {
    private val compositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()

    override fun getMovies(): LiveData<List<Movie>> = movieDao.getAllFavouriteMovies()
    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun loadMovies() {}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}