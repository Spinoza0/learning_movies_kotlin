package com.spinoza.movieskotlin.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.spinoza.movieskotlin.domain.MoviesApiService
import com.spinoza.movieskotlin.domain.MovieDao
import com.spinoza.movieskotlin.domain.links.Link
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.domain.reviews.Review
import com.spinoza.movieskotlin.domain.reviews.ReviewsResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MovieDetailViewModel(
    private val movieDao: MovieDao,
    private val apiService: MoviesApiService,
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val links: MutableLiveData<List<Link>> = MutableLiveData<List<Link>>()
    private val reviews: MutableLiveData<List<Review>> = MutableLiveData<List<Review>>()
    private val error = MutableLiveData<String>()

    fun getFavouriteMovie(id: Int) = movieDao.getFavouriteMovie(id)
    fun getReviews(): LiveData<List<Review>> = reviews
    fun getLinks(): LiveData<List<Link>> = links
    fun isError(): LiveData<String> = error

    fun loadLinks(id: Int) {
        val disposable: Disposable = apiService.loadLinks(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { linkResponse -> linkResponse.linkItemsList.items }
            .subscribe(links::setValue) { throwable -> error.value = throwable.toString() }
        compositeDisposable.add(disposable)
    }

    fun loadReviews(id: Int) {
        val disposable: Disposable = apiService.loadReviews(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(ReviewsResponse::reviews)
            .subscribe(reviews::setValue
            ) { throwable -> error.value = throwable.toString() }
        compositeDisposable.add(disposable)
    }

    fun insertMovie(movie: Movie) {
        val disposable: Disposable = movieDao.insertMovie(movie)
            .subscribeOn(Schedulers.io())
            .subscribe({}) { throwable -> error.value = throwable.toString() }
        compositeDisposable.add(disposable)
    }

    fun removeMovie(movieId: Int) {
        val disposable: Disposable = movieDao.removeMovie(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe({}) { throwable -> error.value = throwable.toString() }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}