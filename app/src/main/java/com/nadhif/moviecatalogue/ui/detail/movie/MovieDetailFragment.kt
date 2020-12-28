package com.nadhif.moviecatalogue.ui.detail.movie

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
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.databinding.FragmentMovieDetailBinding
import com.nadhif.moviecatalogue.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    @Inject
    lateinit var catalogueRepository: CatalogueRepository

    lateinit var binding: FragmentMovieDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private var isFavorite = false

    private val args: MovieDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val movie = args.selectedMovie

        detailViewModel = ViewModelProvider(
            this,
            DetailViewModel.Factory(catalogueRepository, movie)
        ).get(DetailViewModel::class.java)

        binding = FragmentMovieDetailBinding.inflate(inflater)
        detailViewModel.selectedMovie.observe(viewLifecycleOwner, {
            val selectedMovie = it
            detailViewModel.checkFavorite(selectedMovie.id).observe(viewLifecycleOwner, { favorite ->
                if (favorite.isNotEmpty()) {
                    isFavorite = true
                }

                setFavoriteState(isFavorite)
            })
            binding.tvTitleDetail.text = selectedMovie.title
            binding.tvDesc.text = selectedMovie.overview
            binding.tvReleaseDate.text = selectedMovie.releaseDate

            val fullBackdropUrl = Constants.IMAGE_URL + selectedMovie.backdropPath
            Glide.with(this)
                .load(fullBackdropUrl)
                .into(binding.ivPoster2)

            val fullPosterUrl = Constants.IMAGE_URL + selectedMovie.posterPath
            Glide.with(this)
                .load(fullPosterUrl)
                .into(binding.ivPoster1)

            binding.progressBarDetail.visibility = View.GONE

            binding.fabFavorite.setOnClickListener {
                val favoriteMovie = FavoriteMovie(
                    selectedMovie.backdropPath,
                    selectedMovie.id,
                    selectedMovie.overview,
                    selectedMovie.posterPath,
                    selectedMovie.releaseDate,
                    selectedMovie.title
                )
                if (isFavorite) {
                    isFavorite = false
                    detailViewModel.deleteFavoriteMovie(favoriteMovie)
                    setFavoriteState(isFavorite)
                    Toast.makeText(
                        context,
                        "Berhasil Menghapus Favorit",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    isFavorite = true
                    detailViewModel.insertFavoriteMovie(favoriteMovie)
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