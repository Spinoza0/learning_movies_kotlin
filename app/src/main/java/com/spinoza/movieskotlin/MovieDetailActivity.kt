package com.spinoza.movieskotlin

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.databinding.ActivityMovieDetailBinding
import com.spinoza.movieskotlin.links.Link
import com.spinoza.movieskotlin.links.LinksAdapter
import com.spinoza.movieskotlin.movies.Movie
import com.spinoza.movieskotlin.reviews.ReviewsAdapter

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel
    private lateinit var linksAdapter: LinksAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MovieDetailViewModel::class.java]

        linksAdapter = LinksAdapter()
        linksAdapter.onLinkClickListener = object : LinksAdapter.OnLinkClickListener {
            override fun onLinkClick(link: Link) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.url))
                startActivity(intent)
            }
        }

        reviewsAdapter = ReviewsAdapter()
        binding.recyclerViewLinks.adapter = linksAdapter
        binding.recyclerViewReviews.adapter = reviewsAdapter
        if (intent.hasExtra(EXTRA_MOVIE)) {
            intent.getSerializableExtra(EXTRA_MOVIE, Movie::class.java)?.let { setContent(it) }
        } else {
            finish()
        }
    }

    private fun setContent(movie: Movie) {
        with(binding) {
            Glide.with(this@MovieDetailActivity)
                .load(movie.poster.url)
                .into(imageViewPoster)
            textViewName.text = movie.name
            textViewYear.text = movie.year.toString()
            textViewDescription.text = movie.description
            viewModel.loadLinks(movie.id)
            viewModel.getLinks().observe(this@MovieDetailActivity) { links ->
                linksAdapter.setLinks(links)
            }
            viewModel.getReviews().observe(this@MovieDetailActivity) { reviewItems ->
                reviewsAdapter.setReviews(reviewItems)
            }
            viewModel.loadReviews(movie.id)

            val starOff = ContextCompat.getDrawable(
                this@MovieDetailActivity,
                android.R.drawable.star_big_off
            )
            val starOn = ContextCompat.getDrawable(
                this@MovieDetailActivity,
                android.R.drawable.star_big_on
            )

            viewModel.getFavouriteMovie(movie.id)
                .observe(this@MovieDetailActivity) { movieFromDb ->
                    val star: Drawable?
                    if (movieFromDb == null) {
                        star = starOff
                        imageViewStar.setOnClickListener { viewModel.insertMovie(movie) }
                    } else {
                        star = starOn
                        imageViewStar.setOnClickListener { viewModel.removeMovie(movie.id) }
                    }
                    imageViewStar.setImageDrawable(star)
                }
        }
    }

    companion object {
        private const val EXTRA_MOVIE = "movie"

        fun newIntent(context: Context, movie: Movie): Intent {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, movie)
            return intent
        }
    }
}