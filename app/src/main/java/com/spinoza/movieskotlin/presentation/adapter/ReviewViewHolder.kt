package com.spinoza.movieskotlin.presentation.adapter

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R

class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val linearLayoutReviews: LinearLayout = itemView.findViewById(R.id.linearLayoutReviews)
    val textViewAuthor: TextView = itemView.findViewById(R.id.textViewAuthor)
    val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    val textViewReview: TextView = itemView.findViewById(R.id.textViewReview)
}