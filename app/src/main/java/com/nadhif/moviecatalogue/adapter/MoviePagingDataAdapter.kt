package com.nadhif.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.databinding.ItemBinding
import com.nadhif.moviecatalogue.utils.Constants

class MoviePagingDataAdapter(
        private val clickListener: (Movie) -> Unit
): PagingDataAdapter<Movie, MoviePagingDataAdapter.ViewHolder>(DataDifferentiators) {

    object DataDifferentiators: DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.bind(item, clickListener)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie, listener: (Movie) -> Unit) {
            with(binding) {
                val fullImageUrl = Constants.IMAGE_URL + movie.posterPath
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

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

}