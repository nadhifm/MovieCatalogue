package com.nadhif.moviecatalogue.ui.detail.movie

import androidx.lifecycle.*
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import kotlinx.coroutines.launch

class DetailViewModel (
        private val repository: CatalogueRepository,
        movie: Movie
) : ViewModel() {

    private val _selectedMovie = MutableLiveData<Movie>()

    val selectedMovie: LiveData<Movie>
        get() = _selectedMovie

    init {
        _selectedMovie.value = movie
    }

    fun checkFavorite(id: Long) = repository.checkFavorite(id)

    fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch { repository.insertFavoriteMovie(favoriteMovie) }
    fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) = viewModelScope.launch { repository.deleteFavoriteMovie(favoriteMovie) }

    class Factory(
        private val repository: CatalogueRepository,
        val movie: Movie
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailViewModel(repository, movie) as T
            }
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}

