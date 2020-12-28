package com.nadhif.moviecatalogue.data.source.remote

import com.nadhif.moviecatalogue.data.source.remote.response.MovieResponse
import com.nadhif.moviecatalogue.data.source.remote.response.TvShowResponse
import com.nadhif.moviecatalogue.network.ApiService
import com.nadhif.moviecatalogue.utils.EspressoIdlingResource
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val service: ApiService) {
    suspend fun getPopularMovie(page: Int): MovieResponse {
        EspressoIdlingResource.increment()
        try {
            return service.getPopularMovieAsync(page = page).await()
        } finally {
            EspressoIdlingResource.decrement()
        }
    }
    suspend fun getPopularTvShow(page: Int): TvShowResponse {
        EspressoIdlingResource.increment()
        try {
            return service.getPopularTvShowAsync(page = page).await()
        } finally {
            EspressoIdlingResource.decrement()
        }
    }
}