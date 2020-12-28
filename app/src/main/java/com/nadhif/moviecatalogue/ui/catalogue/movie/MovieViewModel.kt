package com.nadhif.moviecatalogue.ui.catalogue.movie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import kotlinx.coroutines.flow.Flow

class MovieViewModel @ViewModelInject constructor(
    private val catalogueRepository: CatalogueRepository
) : ViewModel() {

    fun getMovies(): Flow<PagingData<Movie>> = catalogueRepository.getMovies().cachedIn(viewModelScope)

}