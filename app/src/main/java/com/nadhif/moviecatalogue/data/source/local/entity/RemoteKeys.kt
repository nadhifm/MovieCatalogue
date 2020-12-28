package com.nadhif.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "remote_keys")
data class RemoteKeys(
        @PrimaryKey
        val movieId: Long,
        val prevKey: Int?,
        val nextKey: Int?
) : Parcelable