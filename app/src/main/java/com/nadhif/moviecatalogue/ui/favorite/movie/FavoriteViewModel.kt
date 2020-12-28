package com.nadhif.moviecatalogue.ui.favorite.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.nadhif.moviecatalogue.data.CatalogueRepository

class FavoriteViewModel @ViewModelInject constructor(
    private val catalogueRepository: CatalogueRepository
) : ViewModel() {

    fun getFavoriteMovie() = catalogueRepository.getAllFavoriteMovies()
    fun getFavoriteTvShow() = catalogueRepository.getAllFavoriteTvShows()
}