package com.nadhif.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "tv_show_remote_keys")
data class TvShowRemoteKeys(
        @PrimaryKey
        val tvShowId: Long,
        val prevKey: Int?,
        val nextKey: Int?
) : Parcelable