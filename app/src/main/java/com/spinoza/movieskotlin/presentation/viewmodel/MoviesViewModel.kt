package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.movieskotlin.domain.MoviesApiService
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.presentation.MoviesListShowable
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesViewModel(private val apiService: MoviesApiService) : ViewModel(),
    MoviesListShowable {
    private val movies = MutableLiveData<MutableList<Movie>>()
    private val isLoading = MutableLiveData(false)
    private val error = MutableLiveData<String>()
    private val compositeDisposable = CompositeDisposable()

    private var page = 1

    override fun getMovies(): LiveData<MutableList<Movie>> = movies
    override fun getIsLoading(): LiveData<Boolean> = isLoading

    fun isError(): LiveData<String> = error

    init {
        loadMovies()
    }

    override fun loadMovies() {
        val loading = isLoading.value
        loading?.let {
            if (!it) {
                val disposable: Disposable = apiService.loadMovies(page)
                    .subscribeOn(Schedulers.io())
                    .doOnSubscribe { isLoading.setValue(true) }
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate { isLoading.setValue(false) }
                    .subscribe({ moviesResponse ->
                        val loadedMovies =
                            movies.value ?: moviesResponse.movies.toMutableList()
                        movies.value?.let { loadedMovies.addAll(moviesResponse.movies) }
                        movies.value = loadedMovies
                        page++
                    }) { throwable -> error.value = throwable.toString() }
                compositeDisposable.add(disposable)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}