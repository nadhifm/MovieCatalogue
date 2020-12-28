package com.nadhif.moviecatalogue.ui.catalogue

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.adapter.ViewPagerAdapter
import com.nadhif.moviecatalogue.databinding.FragmentCatalogueBinding
import com.nadhif.moviecatalogue.ui.catalogue.movie.MovieFragment
import com.nadhif.moviecatalogue.ui.catalogue.tvshow.TvShowFragment

class CatalogueFragment : Fragment(R.layout.fragment_catalogue) {


    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: FragmentCatalogueBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogueBinding.bind(view)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        val arrayFragment = arrayListOf(
            MovieFragment(),
            TvShowFragment()
        )

        viewPagerAdapter.setArrayFragment(arrayFragment)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = getString(R.string.movies)
                1 -> tab.text = getString(R.string.tv_shows)
            }
        }.attach()
    }
}