package com.nadhif.moviecatalogue.data.source.remote.response


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TvShowResponse(
    val page: Int,
    val results: List<TvShow>
)

@Parcelize
@Entity(tableName = "tv_show")
data class TvShow(
    @PrimaryKey(autoGenerate = true)
    val tvShowId: Long = 0,
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