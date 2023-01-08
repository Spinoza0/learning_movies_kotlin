package com.spinoza.movieskotlin.data

import com.spinoza.movieskotlin.domain.links.LinkResponse
import com.spinoza.movieskotlin.domain.movies.MoviesResponse
import com.spinoza.movieskotlin.domain.reviews.ReviewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    companion object {
        private const val SPINOZA_TOKEN = "S2TJTAC-GC24QA2-P191MTM-RWK2ZP1"
    }

    @GET("movie?token=$SPINOZA_TOKEN&field=rating.kp&search=5-10&" +
            "sortField=votes.kp&sortType=-1&limit=30")
    fun loadMovies(@Query("page") page: Int): Single<MoviesResponse>

    @GET("movie?token=$SPINOZA_TOKEN&field=id")
    fun loadLinks(@Query("search") id: Int): Single<LinkResponse>

    @GET("review?token=$SPINOZA_TOKEN&field=movieId")
    fun loadReviews(@Query("search") id: Int): Single<ReviewsResponse>
}