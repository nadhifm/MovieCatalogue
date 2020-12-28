package com.nadhif.moviecatalogue.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow

@Dao
interface FavoriteTvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteTvShow: FavoriteTvShow): Long

    @Query("SELECT * FROM favorite_tv_show")
    fun getAllFavoriteTvShows(): LiveData<List<FavoriteTvShow>>

    @Query("SELECT * FROM favorite_tv_show WHERE id = :id")
    fun isFavorite(id: Long): LiveData<List<FavoriteTvShow>>

    @Delete
    suspend fun deleteFavoriteTvShow(favoriteTvShow: FavoriteTvShow)
}