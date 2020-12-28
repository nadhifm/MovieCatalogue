package com.nadhif.moviecatalogue.data.source.remote.response

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class MovieResponse(
        val page: Int,
        val results: List<Movie>
)

@Parcelize
@Entity(tableName = "movie")
data class Movie(
        @PrimaryKey(autoGenerate = true) val movieId: Long = 0,
        @SerializedName("backdrop_path")
        val backdropPath: String? = "",
        val id: Long,
        val overview: String? = "",
        @SerializedName("poster_path")
        val posterPath: String? = "",
        @SerializedName("release_date")
        val releaseDate: String? = "",
        val title: String? = ""
) : Parcelable