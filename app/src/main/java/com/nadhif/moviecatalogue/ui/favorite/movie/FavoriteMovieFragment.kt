package com.nadhif.moviecatalogue.ui.favorite.movie

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.adapter.MovieAdapter
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.databinding.FragmentFavoriteMovieBinding
import com.nadhif.moviecatalogue.ui.favorite.FavoriteFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieFragment : Fragment(R.layout.fragment_favorite_movie) {

    private lateinit var binding: FragmentFavoriteMovieBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter {
            val movie = Movie(
                0,
                it.backdropPath,
                it.id,
                it.overview,
                it.posterPath,
                it.releaseDate,
                it.title
            )
            findNavController().navigate(FavoriteFragmentDirections.actionFavoriteFragmentToMovieDetailFragment(movie))
        }
        binding = FragmentFavoriteMovieBinding.bind(view)

        binding.rvFavoriteMovie.layoutManager = LinearLayoutManager(context)
        binding.rvFavoriteMovie.adapter = movieAdapter

        favoriteViewModel.getFavoriteMovie().observe(viewLifecycleOwner, {
            movieAdapter.submitList(it)
        })
    }
}