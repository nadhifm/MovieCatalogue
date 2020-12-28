package com.nadhif.moviecatalogue.ui.favorite.tvshow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.adapter.TvShowAdapter
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import com.nadhif.moviecatalogue.databinding.FragmentFavoriteTvShowBinding
import com.nadhif.moviecatalogue.ui.favorite.FavoriteFragmentDirections
import com.nadhif.moviecatalogue.ui.favorite.movie.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvShowFragment : Fragment(R.layout.fragment_favorite_tv_show) {

    lateinit var binding: FragmentFavoriteTvShowBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var tvShowAdapter: TvShowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvShowAdapter = TvShowAdapter {
            val tvShow = TvShow(
                0,
                it.id,
                it.firstAirDate,
                it.name,
                it.overview,
                it.posterPath,
                it.backdropPath
            )
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToTvShowDetailFragment(tvShow))
        }
        binding = FragmentFavoriteTvShowBinding.bind(view)

        binding.rvFavoriteTvShow.layoutManager = LinearLayoutManager(context)
        binding.rvFavoriteTvShow.adapter = tvShowAdapter

        favoriteViewModel.getFavoriteTvShow().observe(viewLifecycleOwner, {
            tvShowAdapter.submitList(it)
        })

    }
}