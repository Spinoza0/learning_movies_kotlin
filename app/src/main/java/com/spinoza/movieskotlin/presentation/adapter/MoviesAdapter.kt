package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.domain.movies.Movie
import java.util.*

class MoviesAdapter : ListAdapter<Movie, MovieViewHolder>(MoviesDiffCallback()) {
    var onReachEndListener: (() -> Unit)? = null
    var onMovieClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                Glide.with(itemView).load(poster.url).into(imageViewPoster)
                with(rating.kinopoiskRating) {
                    val backgroundId = if (this > 7) {
                        R.drawable.circle_green
                    } else if (this > 5) {
                        R.drawable.circle_orange
                    } else {
                        R.drawable.circle_red
                    }
                    textViewRating.background = ContextCompat.getDrawable(
                        itemView.context, backgroundId
                    )
                    textViewRating.text =
                        String.format(Locale.getDefault(), "%.1f", this)
                }

                if (position >= itemCount - 10) {
                    onReachEndListener?.invoke()
                }
                itemView.setOnClickListener { onMovieClickListener?.invoke(this) }
            }
        }
    }
}