package com.nadhif.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_movie")
data class FavoriteMovie(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @PrimaryKey
    val id: Long,
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
) : Parcelable
