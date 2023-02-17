package com.spinoza.movieskotlin.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesApiFactory {
    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @JvmField
    val apiService: MoviesApiService = retrofit.create(MoviesApiService::class.java)
}
