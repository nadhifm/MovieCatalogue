package com.nadhif.moviecatalogue.data.source.local.room

import androidx.paging.PagingSource
import androidx.room.*
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(tvShow: List<TvShow>)

    @Query("SELECT * FROM tv_show ORDER BY tvShowId ASC")
    fun getAllTvShow(): PagingSource<Int, TvShow>

    @Query("DELETE FROM tv_show")
    suspend fun clearTvShow()
}