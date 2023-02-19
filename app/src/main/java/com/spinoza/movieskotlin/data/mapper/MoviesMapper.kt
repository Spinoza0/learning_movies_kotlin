package com.spinoza.movieskotlin.data.mapper

import com.spinoza.movieskotlin.data.database.model.MovieDbModel
import com.spinoza.movieskotlin.data.network.model.links.LinkDto
import com.spinoza.movieskotlin.data.network.model.movies.MovieDto
import com.spinoza.movieskotlin.data.network.model.movies.OneMovieResponseDto
import com.spinoza.movieskotlin.data.network.model.reviews.ReviewDto
import com.spinoza.movieskotlin.domain.model.Link
import com.spinoza.movieskotlin.domain.model.Movie
import com.spinoza.movieskotlin.domain.model.Review
import java.util.*

class MoviesMapper {

    private val convertRating = { rating: Double ->
        String.format(Locale.getDefault(), "%.1f", rating)
    }


    private fun mapDtoToEntity(movieDto: MovieDto): Movie {
        return Movie(
            id = movieDto.id,
            year = movieDto.year,
            name = movieDto.name,
            description = movieDto.description,
            poster = movieDto.poster.url,
            rating = movieDto.rating.kinopoiskRating,
            ratingText = convertRating(movieDto.rating.kinopoiskRating)
        )
    }

    fun mapDtoToEntity(movieResponseDto: OneMovieResponseDto): Movie {
        return Movie(
            id = movieResponseDto.id,
            year = movieResponseDto.year,
            name = movieResponseDto.name,
            description = movieResponseDto.description,
            poster = movieResponseDto.poster.url,
            rating = movieResponseDto.rating.kinopoiskRating,
            ratingText = convertRating(movieResponseDto.rating.kinopoiskRating)
        )
    }

    fun mapMoviesDtoToEntity(moviesDto: List<MovieDto>): List<Movie> =
        moviesDto.map { mapDtoToEntity(it) }

    private fun mapDtoToEntity(linkDto: LinkDto): Link = Link(
        name = linkDto.name,
        url = linkDto.url
    )

    fun mapLinksDtoToEntity(linksDto: List<LinkDto>): List<Link> = linksDto.map {
        mapDtoToEntity(it)
    }

    private fun mapDtoToEntity(reviewDto: ReviewDto): Review = Review(
        title = reviewDto.title ?: "",
        type = reviewDto.type,
        review = reviewDto.review,
        author = reviewDto.author
    )

    fun mapReviewsDtoToEntity(reviewsDto: List<ReviewDto>): List<Review> = reviewsDto.map {
        mapDtoToEntity(it)
    }

    private fun mapDbModelToEntity(movieDbModel: MovieDbModel): Movie = Movie(
        id = movieDbModel.id,
        year = movieDbModel.year,
        name = movieDbModel.name,
        description = movieDbModel.description,
        poster = movieDbModel.poster,
        rating = movieDbModel.rating,
        ratingText = convertRating(movieDbModel.rating)
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