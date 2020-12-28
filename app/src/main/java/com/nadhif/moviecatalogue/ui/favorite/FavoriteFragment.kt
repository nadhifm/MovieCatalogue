package com.nadhif.moviecatalogue.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.adapter.ViewPagerAdapter
import com.nadhif.moviecatalogue.databinding.FragmentFavoriteBinding
import com.nadhif.moviecatalogue.ui.favorite.movie.FavoriteMovieFragment
import com.nadhif.moviecatalogue.ui.favorite.tvshow.FavoriteTvShowFragment

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var binding: FragmentFavoriteBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavoriteBinding.bind(view)
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter

        val arrayFragment = arrayListOf(
            FavoriteMovieFragment(),
            FavoriteTvShowFragment()
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