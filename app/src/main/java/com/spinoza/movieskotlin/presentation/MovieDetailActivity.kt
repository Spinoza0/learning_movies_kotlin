package com.spinoza.movieskotlin.presentation

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.data.MovieDatabase
import com.spinoza.movieskotlin.data.MoviesApiFactory
import com.spinoza.movieskotlin.databinding.ActivityMovieDetailBinding
import com.spinoza.movieskotlin.domain.movies.Movie
import com.spinoza.movieskotlin.presentation.adapter.LinksAdapter
import com.spinoza.movieskotlin.presentation.adapter.ReviewsAdapter
import com.spinoza.movieskotlin.presentation.viewmodel.MovieDetailViewModel
import com.spinoza.movieskotlin.presentation.viewmodel.MovieDetailViewModelFactory

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var linksAdapter: LinksAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MovieDetailViewModelFactory(
                MovieDatabase.getInstance(application).movieDao(),
                MoviesApiFactory.apiService
            )
        )[MovieDetailViewModel::class.java]

        linksAdapter = LinksAdapter()
        linksAdapter.onLinkClickListener = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            startActivity(intent)
        }

        reviewsAdapter = ReviewsAdapter()
        binding.recyclerViewLinks.adapter = linksAdapter
        binding.recyclerViewReviews.adapter = reviewsAdapter
        if (intent.hasExtra(EXTRA_MOVIE)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_MOVIE, Movie::class.java)?.let { setContent(it) }
            } else {
                @Suppress("deprecation")
                setContent(intent.getParcelableExtra<Movie>(EXTRA_MOVIE) as Movie)
            }
        } else {
            finish()
        }
    }

    private fun setContent(movie: Movie) {
        with(binding) {
            with(viewModel) {
                Glide.with(this@MovieDetailActivity)
                    .load(movie.poster.url)
                    .into(imageViewPoster)
                textViewName.text = movie.name
                textViewYear.text = movie.year.toString()
                textViewDescription.text = movie.description
                loadLinks(movie.id)
                links.observe(this@MovieDetailActivity) { linksAdapter.submitList(it) }
                reviews.observe(this@MovieDetailActivity) { reviewsAdapter.submitList(it) }
                loadReviews(movie.id)

                val starOff = ContextCompat.getDrawable(
                    this@MovieDetailActivity,
                    android.R.drawable.star_big_off
                )
                val starOn = ContextCompat.getDrawable(
                    this@MovieDetailActivity,
                    android.R.drawable.star_big_on
                )

                getFavouriteMovie(movie.id).observe(this@MovieDetailActivity) { movieFromDb ->
                    val star: Drawable?
                    if (movieFromDb == null) {
                        star = starOff
                        imageViewStar.setOnClickListener { insertMovie(movie) }
                    } else {
                        star = starOn
                        imageViewStar.setOnClickListener { removeMovie(movie.id) }
                    }
                    imageViewStar.setImageDrawable(star)
                }
                error.observe(this@MovieDetailActivity) {
                    Toast.makeText(this@MovieDetailActivity, it, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movie: Movie): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movie)
            }
        }
    }
}