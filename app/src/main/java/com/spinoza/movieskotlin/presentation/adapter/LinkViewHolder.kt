package com.spinoza.movieskotlin.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.spinoza.movieskotlin.R

class LinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textViewLinkName: TextView = itemView.findViewById(R.id.textViewName)
}