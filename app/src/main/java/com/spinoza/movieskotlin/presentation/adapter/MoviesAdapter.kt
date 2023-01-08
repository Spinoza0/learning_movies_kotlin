package com.spinoza.movieskotlin.presentation.adapter

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
import com.spinoza.movieskotlin.domain.movies.Movie
import java.util.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {
    private var movies: List<Movie> = ArrayList()
    var onReachEndListener: (() -> Unit)? = null
    var onMovieClickListener: ((Movie) -> Unit)? = null

    fun setMovies(movies: List<Movie>) {
        val diffUtilCallback = DiffUtilCallback(this.movies, movies)
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
                    onReachEndListener?.invoke()
                }
                itemView.setOnClickListener { onMovieClickListener?.invoke(this) }
            }
        }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
        val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
    }

    private class DiffUtilCallback(
        private val oldList: List<Movie>,
        private val newList: List<Movie>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size
        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.year == newItem.year &&
                    oldItem.name == newItem.name &&
                    oldItem.description == newItem.description
        }
    }
}