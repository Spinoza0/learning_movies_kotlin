package com.spinoza.movieskotlin.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.spinoza.movieskotlin.databinding.LinkItemBinding
import com.spinoza.movieskotlin.domain.model.Link

class LinksAdapter : ListAdapter<Link, LinkViewHolder>(LinkDiffCallback()) {

    var onLinkClickListener: ((Link) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        val binding = LinkItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LinkViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        with(getItem(position)) {
            holder.binding.textViewName.text = name
            holder.itemView.setOnClickListener { onLinkClickListener?.invoke(this) }
        }
    }
}