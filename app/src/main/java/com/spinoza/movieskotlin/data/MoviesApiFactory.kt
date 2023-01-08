package com.spinoza.movieskotlin.data

import com.spinoza.movieskotlin.domain.MoviesApiService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MoviesApiFactory {
    private const val BASE_URL = "https://api.kinopoisk.dev/"
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    @JvmField
    val apiService: MoviesApiService = retrofit.create(MoviesApiService::class.java)
}
