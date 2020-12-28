package com.nadhif.moviecatalogue.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.nadhif.moviecatalogue.data.source.remote.response.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movie: List<Movie>)

    @Query("SELECT * FROM movie ORDER BY movieId ASC")
    fun getAllMovies(): PagingSource<Int, Movie>

    @Query("DELETE FROM movie")
    suspend fun clearMovie()
}