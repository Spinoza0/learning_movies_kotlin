package com.spinoza.movieskotlin.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.spinoza.movieskotlin.domain.model.Link

class LinkDiffCallback : DiffUtil.ItemCallback<Link>() {
    override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
        return oldItem.url == newItem.url
    }
}