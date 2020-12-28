package com.nadhif.moviecatalogue.network

import com.nadhif.moviecatalogue.BuildConfig
import com.nadhif.moviecatalogue.data.source.remote.response.MovieResponse
import com.nadhif.moviecatalogue.data.source.remote.response.TvShowResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = BuildConfig.TOKEN

interface ApiService {
    @GET("movie/popular")
    fun getPopularMovieAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int?
    ): Deferred<MovieResponse>

    @GET("tv/popular")
    fun getPopularTvShowAsync(
        @Query("api_key") api_key: String = API_KEY,
        @Query("page") page: Int?
    ): Deferred<TvShowResponse>
}

