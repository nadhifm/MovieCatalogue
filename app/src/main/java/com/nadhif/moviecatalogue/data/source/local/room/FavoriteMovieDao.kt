package com.nadhif.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteMovie: FavoriteMovie): Long

    @Query("SELECT * FROM favorite_movie")
    fun getAllFavoriteMovies(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM favorite_movie WHERE id = :id")
    fun isFavorite(id: Long): LiveData<List<FavoriteMovie>>

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie)
}