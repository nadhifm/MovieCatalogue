package com.nadhif.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.databinding.ItemBinding
import com.nadhif.moviecatalogue.utils.Constants.Companion.IMAGE_URL

class MovieAdapter(private val clickListener: (FavoriteMovie) -> Unit)
    : ListAdapter<FavoriteMovie, MovieAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<FavoriteMovie>() {

        override fun areItemsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavoriteMovie, newItem: FavoriteMovie): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovie, listener: (FavoriteMovie) -> Unit) {
            with(binding) {
                val fullImageUrl = IMAGE_URL + movie.posterPath
                Glide.with(this.root)
                        .load(fullImageUrl)
                        .into(ivPoster)
                tvTitle.text = movie.title
                tvDesc.text = movie.overview
                root.setOnClickListener {
                    listener(movie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie, clickListener)
    }

}