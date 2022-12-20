package com.spinoza.movieskotlin.movies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.spinoza.movieskotlin.R
import java.util.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var movies: List<Movie> = ArrayList()
    var onReachEndListener: OnReachEndListener? = null
    var onMovieClickListener: OnMovieClickListener? = null

    fun setMovies(movies: List<Movie>) {
        val diffUtilCallback = MoviesDiffUtilCallback(this.movies, movies)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        this.movies = movies
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = movies.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item,
            parent,
            false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            with(movies[position]) {
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

                if (position >= movies.size - 10) {
                    onReachEndListener?.onReachEnd()
                }
                itemView.setOnClickListener { onMovieClickListener?.onMovieClick(this) }
            }
        }
    }

    interface OnReachEndListener {
        fun onReachEnd()
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView
        val textViewRating: TextView

        init {
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster)
            textViewRating = itemView.findViewById(R.id.textViewRating)
        }
    }
}