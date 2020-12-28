package com.nadhif.moviecatalogue.ui.catalogue.tvshow

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
import com.nadhif.moviecatalogue.adapter.TvShowPagingDataAdapter
import com.nadhif.moviecatalogue.databinding.FragmentTvShowBinding
import com.nadhif.moviecatalogue.ui.catalogue.CatalogueFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment : Fragment(R.layout.fragment_tv_show) {

    private lateinit var binding: FragmentTvShowBinding
    private lateinit var tvShowAdapter: TvShowPagingDataAdapter
    private val tvShowViewModel by viewModels<TvShowViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTvShowBinding.bind(view)

        tvShowAdapter = TvShowPagingDataAdapter {
            findNavController().navigate(CatalogueFragmentDirections.actionCatalogueFragmentToTvShowDetailFragment(it))
        }

        viewLifecycleOwner.lifecycleScope.launch {
            tvShowViewModel.getPopularTvShow().collectLatest {
                tvShowAdapter.submitData(it)
            }
        }

        binding.rvTvShow.layoutManager = LinearLayoutManager(context)
        binding.rvTvShow.adapter = tvShowAdapter.withLoadStateFooter(
                footer = FooterLoadStateAdapter { tvShowAdapter.retry() }
        )

        tvShowAdapter.addLoadStateListener { loadState ->
            binding.progressBarTvShow.isVisible = loadState.source.refresh is LoadState.Loading
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