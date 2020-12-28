package com.nadhif.moviecatalogue.ui.catalogue.movie

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.adapter.FooterLoadStateAdapter
import com.nadhif.moviecatalogue.adapter.MoviePagingDataAdapter
import com.nadhif.moviecatalogue.databinding.FragmentMovieBinding
import com.nadhif.moviecatalogue.ui.catalogue.CatalogueFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter: MoviePagingDataAdapter
    private val movieViewModel by viewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMovieBinding.bind(view)

        movieAdapter = MoviePagingDataAdapter {
            findNavController().navigate(CatalogueFragmentDirections.actionCatalogueFragmentToMovieDetailFragment(it))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            movieViewModel.getMovies().collectLatest {
                movieAdapter.submitData(it)
            }
        }

        binding.rvMovie.layoutManager = LinearLayoutManager(context)
        binding.rvMovie.adapter = movieAdapter.withLoadStateFooter(
            footer = FooterLoadStateAdapter { movieAdapter.retry() }
        )

        movieAdapter.addLoadStateListener { loadState ->
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
            errorState?.let {
                Toast.makeText(
                    requireContext(),
                    "${it.error}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}