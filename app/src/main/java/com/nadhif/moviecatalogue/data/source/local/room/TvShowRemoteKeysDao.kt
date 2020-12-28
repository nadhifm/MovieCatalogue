package com.nadhif.moviecatalogue.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nadhif.moviecatalogue.data.source.local.entity.TvShowRemoteKeys

@Dao
interface TvShowRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<TvShowRemoteKeys>)

    @Query("SELECT * FROM tv_show_remote_keys WHERE tvShowId = :tvShowId")
    suspend fun remoteKeysTVShowId(tvShowId: Long): TvShowRemoteKeys?

    @Query("DELETE FROM tv_show_remote_keys")
    suspend fun clearRemoteKeys()
}