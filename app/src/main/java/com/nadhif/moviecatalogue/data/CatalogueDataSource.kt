package com.nadhif.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import kotlinx.coroutines.flow.Flow

interface CatalogueDataSource {
    fun getMovies(): Flow<PagingData<Movie>>
    fun getPopularTvShow(): Flow<PagingData<TvShow>>
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie): Long
    fun getAllFavoriteMovies():  LiveData<List<FavoriteMovie>>
    fun checkFavorite(id: Long): LiveData<List<FavoriteMovie>>
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie)
    suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShow): Long
    fun getAllFavoriteTvShows():  LiveData<List<FavoriteTvShow>>
    fun checkFavoriteTvShow(id: Long):  LiveData<List<FavoriteTvShow>>
    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShow)
}