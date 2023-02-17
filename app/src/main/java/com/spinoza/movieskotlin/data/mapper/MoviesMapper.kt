package com.spinoza.movieskotlin.data.mapper

import com.spinoza.movieskotlin.data.database.MovieDao
import com.spinoza.movieskotlin.data.database.model.MovieDbModel
import com.spinoza.movieskotlin.data.network.model.links.LinkDto
import com.spinoza.movieskotlin.data.network.model.movies.MovieDto
import com.spinoza.movieskotlin.data.network.model.reviews.ReviewDto
import com.spinoza.movieskotlin.domain.model.Link
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.Review

class MoviesMapper(private val movieDao: MovieDao) {

    private suspend fun mapDtoToEntity(movieDto: MovieDto): Movie {
        val isFavourite = try {
            movieDao.isMovieFavourite(movieDto.id)
        } catch (e: Exception) {
            false
        }

        return Movie(
            id = movieDto.id,
            year = movieDto.year,
            name = movieDto.name,
            description = movieDto.description,
            poster = movieDto.poster.url,
            rating = movieDto.rating.kinopoiskRating,
            isFavourite = isFavourite
        )
    }

    suspend fun mapDtoToEntity(moviesDto: List<MovieDto>): List<Movie> =
        moviesDto.map {
            mapDtoToEntity(it)
        }

    private fun mapDtoToEntity(linkDto: LinkDto): Link = Link(
        name = linkDto.name,
        url = linkDto.url
    )

    fun mapDtoToEntity(linksDto: List<LinkDto>): List<Link> = linksDto.map {
        mapDtoToEntity(it)
    }

    private fun mapDtoToEntity(reviewDto: ReviewDto): Review = Review(
        title = reviewDto.title,
        type = reviewDto.type,
        review = reviewDto.review,
        author = reviewDto.author
    )

    fun mapDtoToEntity(reviewsDto: List<ReviewDto>): List<Review> = reviewsDto.map {
        mapDtoToEntity(it)
    }

    fun mapDbModelToEntity(movieDbModel: MovieDbModel): Movie = Movie(
        id = movieDbModel.id,
        year = movieDbModel.year,
        name = movieDbModel.name,
        description = movieDbModel.description,
        poster = movieDbModel.poster,
        rating = movieDbModel.rating,
        isFavourite = true
    )

    fun mapDbModelToEntity(moviesDbModel: List<MovieDbModel>): List<Movie> =
        moviesDbModel.map {
            mapDbModelToEntity(it)
        }

    fun mapEntityToDbModel(movie: Movie): MovieDbModel = MovieDbModel(
        id = movie.id,
        year = movie.year,
        name = movie.name,
        description = movie.description,
        poster = movie.poster,
        rating = movie.rating
    )
}