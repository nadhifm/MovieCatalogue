package com.nadhif.moviecatalogue.data.source.local

import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.local.room.FavoriteMovieDao
import com.nadhif.moviecatalogue.data.source.local.room.FavoriteTvShowDao
import com.nadhif.moviecatalogue.data.source.local.room.MovieDao
import com.nadhif.moviecatalogue.data.source.local.room.TvShowDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val favoriteTvShowDao: FavoriteTvShowDao
) {
    fun getAllMovies() = movieDao.getAllMovies()

    fun getAllTvShow() = tvShowDao.getAllTvShow()

    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = favoriteMovieDao.insert(favoriteMovie)
    fun getAllFavoriteMovies() = favoriteMovieDao.getAllFavoriteMovies()
    fun isFavoriteMovie(id: Long) = favoriteMovieDao.isFavorite(id)
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) = favoriteMovieDao.deleteFavoriteMovie(favoriteMovie)

    suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = favoriteTvShowDao.insert(favoriteTvShow)
    fun getAllFavoriteTvShows() = favoriteTvShowDao.getAllFavoriteTvShows()
    fun isFavoriteTvShow(id: Long) = favoriteTvShowDao.isFavorite(id)
    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = favoriteTvShowDao.deleteFavoriteTvShow(favoriteTvShow)
}