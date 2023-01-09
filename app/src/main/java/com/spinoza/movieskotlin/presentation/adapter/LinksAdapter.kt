package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.movieskotlin.R
import com.spinoza.movieskotlin.domain.links.Link

class LinksAdapter : ListAdapter<Link, LinkViewHolder>(LinkDiffCallback()) {

    var onLinkClickListener: ((Link) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.link_item,
            parent,
            false)
        return LinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        with(getItem(position)) {
            holder.textViewLinkName.text = name
            holder.itemView.setOnClickListener { onLinkClickListener?.invoke(this) }
        }
    }
}