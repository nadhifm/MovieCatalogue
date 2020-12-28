package com.nadhif.moviecatalogue.ui.detail.tvshow

import androidx.lifecycle.*
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import kotlinx.coroutines.launch

class TvShowDetailViewModel (
    private val repository: CatalogueRepository,
    tvShow: TvShow
) : ViewModel() {

    private val _selectedTvShow = MutableLiveData<TvShow>()

    val selectedTvShow: LiveData<TvShow>
        get() = _selectedTvShow

    init {
        _selectedTvShow.value = tvShow
    }

    fun checkFavorite(id: Long) = repository.checkFavoriteTvShow(id)

    fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = viewModelScope.launch { repository.insertFavoriteTvShow(favoriteTvShow) }
    fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = viewModelScope.launch { repository.deleteFavoriteTvShow(favoriteTvShow) }

    class Factory(
        private val repository: CatalogueRepository,
        private val tvShow: TvShow
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TvShowDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TvShowDetailViewModel(repository, tvShow) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}