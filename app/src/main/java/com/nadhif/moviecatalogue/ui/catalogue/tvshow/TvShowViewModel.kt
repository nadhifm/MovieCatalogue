package com.nadhif.moviecatalogue.ui.catalogue.tvshow

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import kotlinx.coroutines.flow.Flow

class TvShowViewModel @ViewModelInject constructor(
    private val catalogueRepository: CatalogueRepository
) : ViewModel() {

    fun getPopularTvShow(): Flow<PagingData<TvShow>> = catalogueRepository.getPopularTvShow().cachedIn(viewModelScope)
}