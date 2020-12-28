package com.nadhif.moviecatalogue.data.source.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "favorite_tv_show")
data class FavoriteTvShow(
    @PrimaryKey
    val id: Long,
    @SerializedName("first_air_date")
    val firstAirDate: String? = "",
    val name: String? = "",
    val overview: String? = "",
    @SerializedName("poster_path")
    val posterPath: String? = "",
    @SerializedName("backdrop_path")
    val backdropPath: String? = ""
) : Parcelable