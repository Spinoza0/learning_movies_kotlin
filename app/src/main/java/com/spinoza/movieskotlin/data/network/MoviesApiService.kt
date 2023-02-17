package com.spinoza.movieskotlin.data.network

import com.spinoza.movieskotlin.data.network.model.links.LinkResponseDto
import com.spinoza.movieskotlin.data.network.model.movies.MoviesResponseDto
import com.spinoza.movieskotlin.data.network.model.reviews.ReviewsResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {
    companion object {
        private const val API_TOKEN = "S2TJTAC-GC24QA2-P191MTM-RWK2ZP1"
    }

    @GET(
        "movie?token=$API_TOKEN&field=rating.kp&search=5-10&" +
                "sortField=votes.kp&sortType=-1&limit=30"
    )
    suspend fun loadMovies(@Query("page") page: Int): Response<MoviesResponseDto>

    @GET("movie?token=$API_TOKEN&field=id")
    suspend fun loadLinks(@Query("search") id: Int): Response<LinkResponseDto>

    @GET("review?token=$API_TOKEN&field=movieId")
    suspend fun loadReviews(@Query("search") id: Int): Response<ReviewsResponseDto>
}