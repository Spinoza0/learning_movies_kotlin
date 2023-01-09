package com.spinoza.movieskotlin.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R

class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val imageViewPoster: ImageView = itemView.findViewById(R.id.imageViewPoster)
    val textViewRating: TextView = itemView.findViewById(R.id.textViewRating)
}
