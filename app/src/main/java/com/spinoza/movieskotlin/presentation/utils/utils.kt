package com.spinoza.movieskotlin.presentation.utils

import android.content.Context
import android.widget.TextView
import com.spinoza.movieskotlin.domain.model.Movie

fun setupTextViewRating(context: Context, textView: TextView, movie: Movie) {
    with(movie.rating) {
        val backgroundId = if (this > 7) {
            com.spinoza.movieskotlin.R.drawable.circle_green
        } else if (this > 5) {
            com.spinoza.movieskotlin.R.drawable.circle_orange
        } else {
            com.spinoza.movieskotlin.R.drawable.circle_red
        }
        textView.background = androidx.core.content.ContextCompat.getDrawable(
            context, backgroundId
        )
    }
    textView.text = movie.ratingText
}