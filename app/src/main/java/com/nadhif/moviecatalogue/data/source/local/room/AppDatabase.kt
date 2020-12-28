package com.nadhif.moviecatalogue.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadhif.moviecatalogue.data.source.local.entity.RemoteKeys
import com.nadhif.moviecatalogue.data.source.local.entity.TvShowRemoteKeys
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow

@Database(
    entities = [
        Movie::class,
        RemoteKeys::class,
        TvShow::class,
        TvShowRemoteKeys::class,
        FavoriteMovie::class,
        FavoriteTvShow::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun tvShowRemoteKeysDao(): TvShowRemoteKeysDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun favoriteMovieDao(): FavoriteMovieDao
    abstract fun favoriteTvShowDao(): FavoriteTvShowDao
}