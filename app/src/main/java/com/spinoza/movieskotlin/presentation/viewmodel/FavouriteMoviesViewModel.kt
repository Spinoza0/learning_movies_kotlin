package com.spinoza.movieskotlin.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.spinoza.movieskotlin.data.MovieDao
import com.spinoza.movieskotlin.data.MovieDatabase
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.presentation.MoviesListShowable
import io.reactivex.rxjava3.disposables.CompositeDisposable

class FavouriteMoviesViewModel(application: Application) : AndroidViewModel(application),
    MoviesListShowable {
    private val movieDao: MovieDao
    private val compositeDisposable = CompositeDisposable()
    private val isLoading = MutableLiveData<Boolean>()

    init {
        movieDao = MovieDatabase.getInstance(application).movieDao()
    }

    override fun getMovies(): LiveData<MutableList<Movie>> = movieDao.getAllFavouriteMovies()
    override fun getIsLoading(): LiveData<Boolean> = isLoading
    override fun loadMovies() {}

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}