package com.nadhif.moviecatalogue.ui.detail.tvshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.databinding.FragmentTvShowDetailBinding
import com.nadhif.moviecatalogue.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TvShowDetailFragment : Fragment() {

    @Inject
    lateinit var catalogueRepository: CatalogueRepository

    lateinit var binding: FragmentTvShowDetailBinding
    private lateinit var tvShowDetailViewModel: TvShowDetailViewModel
    private var isFavorite = false

    private val args: TvShowDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val tvShow = args.selectedTvShow
        tvShowDetailViewModel = ViewModelProvider(
            this,
            TvShowDetailViewModel.Factory(catalogueRepository, tvShow)
        ).get(TvShowDetailViewModel::class.java)

        binding = FragmentTvShowDetailBinding.inflate(inflater)
        tvShowDetailViewModel.selectedTvShow.observe(viewLifecycleOwner, { selectedTvShow ->
            tvShowDetailViewModel.checkFavorite(selectedTvShow.id).observe(viewLifecycleOwner, { favorite ->
                if (favorite.isNotEmpty()) {
                    isFavorite = true
                }

                setFavoriteState(isFavorite)
            })
            binding.tvTitleDetail.text = selectedTvShow.name
            binding.tvDesc.text = selectedTvShow.overview
            binding.tvReleaseDate.text = selectedTvShow.firstAirDate

            val fullBackdropUrl = Constants.IMAGE_URL + selectedTvShow.backdropPath
            Glide.with(this)
                .load(fullBackdropUrl)
                .into(binding.ivPoster2)

            val fullPosterUrl = Constants.IMAGE_URL + selectedTvShow.posterPath
            Glide.with(this)
                .load(fullPosterUrl)
                .into(binding.ivPoster1)

            binding.progressBarDetail.visibility = View.GONE

            binding.fabFavorite.setOnClickListener {
                val favoriteTvShow = FavoriteTvShow(
                    selectedTvShow.id,
                    selectedTvShow.firstAirDate,
                    selectedTvShow.name,
                    selectedTvShow.overview,
                    selectedTvShow.posterPath,
                    selectedTvShow.backdropPath
                )
                if (isFavorite) {
                    isFavorite = false
                    tvShowDetailViewModel.deleteFavoriteTvShow(favoriteTvShow)
                    setFavoriteState(isFavorite)
                    Toast.makeText(
                        context,
                        "Berhasil Menghapus Favorit",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    isFavorite = true
                    tvShowDetailViewModel.insertFavoriteTvShow(favoriteTvShow)
                    setFavoriteState(isFavorite)
                    Toast.makeText(
                        context,
                        "Berhasil Menambahkan Favorit",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })

        return binding.root
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite_red)
        } else {
            binding.fabFavorite.setImageResource(R.drawable.ic_favorite)
        }
    }
}