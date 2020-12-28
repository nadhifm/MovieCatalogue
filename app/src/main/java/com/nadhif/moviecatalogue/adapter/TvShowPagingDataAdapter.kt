package com.nadhif.moviecatalogue.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import com.nadhif.moviecatalogue.databinding.ItemBinding
import com.nadhif.moviecatalogue.utils.Constants

class TvShowPagingDataAdapter(private val clickListener: (TvShow) -> Unit) : PagingDataAdapter<TvShow, TvShowPagingDataAdapter.ViewHolder>(DataDifferentiators) {
    object DataDifferentiators: DiffUtil.ItemCallback<TvShow>() {
        override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
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

        fun bind(tvShow: TvShow, listener: (TvShow) -> Unit) {
            with(binding) {
                val fullImageUrl = Constants.IMAGE_URL + tvShow.posterPath
                Glide.with(this.root)
                        .load(fullImageUrl)
                        .into(ivPoster)
                tvTitle.text = tvShow.name
                tvDesc.text = tvShow.overview
                root.setOnClickListener {
                    listener(tvShow)
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