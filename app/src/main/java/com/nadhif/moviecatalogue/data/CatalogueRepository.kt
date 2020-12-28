package com.nadhif.moviecatalogue.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nadhif.moviecatalogue.data.source.local.LocalDataSource
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.remote.remotemediator.MovieRemoteMediator
import com.nadhif.moviecatalogue.data.source.remote.remotemediator.TvShowRemoteMediator
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import com.nadhif.moviecatalogue.utils.Constants.Companion.NETWORK_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CatalogueRepository @Inject constructor(
        private val movieRemoteMediator: MovieRemoteMediator,
        private val tvShowRemoteMediator: TvShowRemoteMediator,
        private val localDataSource: LocalDataSource
) : CatalogueDataSource {

    override fun getMovies(): Flow<PagingData<Movie>> {
        val pagingSource = { localDataSource.getAllMovies() }
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = movieRemoteMediator,
            pagingSourceFactory = pagingSource
        ).flow
    }

    override fun getPopularTvShow(): Flow<PagingData<TvShow>> {

        val pagingSource = { localDataSource.getAllTvShow() }
        return Pager(
                config = PagingConfig(
                        pageSize = NETWORK_PAGE_SIZE,
                        enablePlaceholders = true
                ),
                remoteMediator = tvShowRemoteMediator,
                pagingSourceFactory = pagingSource
        ).flow
    }

    override suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie) = localDataSource.insertFavoriteMovie(favoriteMovie)
    override fun getAllFavoriteMovies() = localDataSource.getAllFavoriteMovies()
    override fun checkFavorite(id: Long) = localDataSource.isFavoriteMovie(id)
    override suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) = localDataSource.deleteFavoriteMovie(favoriteMovie)

    override suspend fun insertFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = localDataSource.insertFavoriteTvShow(favoriteTvShow)
    override fun getAllFavoriteTvShows() = localDataSource.getAllFavoriteTvShows()
    override fun checkFavoriteTvShow(id: Long) = localDataSource.isFavoriteTvShow(id)
    override suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShow) = localDataSource.deleteFavoriteTvShow(favoriteTvShow)
}