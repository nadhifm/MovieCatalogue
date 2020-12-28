package com.nadhif.moviecatalogue.data.source.remote.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.nadhif.moviecatalogue.data.source.local.entity.TvShowRemoteKeys
import com.nadhif.moviecatalogue.data.source.local.room.AppDatabase
import com.nadhif.moviecatalogue.data.source.remote.RemoteDataSource
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@OptIn(ExperimentalPagingApi::class)
class TvShowRemoteMediator @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appDatabase: AppDatabase
) : RemoteMediator<Int, TvShow>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, TvShow>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: // The LoadType is PREPEND so some data was loaded before,
                    // so we should have been able to get remote keys
                    // If the remoteKeys are null, then we're an invalid state and we have a bug
                    return MediatorResult.Success(endOfPaginationReached = true)
                // If the previous key is null, then we can't request more data
                if (remoteKeys.prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.nextKey
            }
        }

        try {
            val apiResponse = remoteDataSource.getPopularTvShow(page = page)

            val tvShows = apiResponse.results
            val endOfPaginationReached = tvShows.isEmpty()
            appDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    appDatabase.tvShowRemoteKeysDao().clearRemoteKeys()
                    appDatabase.tvShowDao().clearTvShow()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = tvShows.map {
                    TvShowRemoteKeys(tvShowId = it.id, prevKey = prevKey, nextKey = nextKey)
                }
                appDatabase.tvShowRemoteKeysDao().insertAll(keys)
                appDatabase.tvShowDao().insertAll(tvShows)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, TvShow>): TvShowRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShow ->
                // Get the remote keys of the last item retrieved
                appDatabase.tvShowRemoteKeysDao().remoteKeysTVShowId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TvShow>): TvShowRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShow ->
                // Get the remote keys of the first items retrieved
                appDatabase.tvShowRemoteKeysDao().remoteKeysTVShowId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShow>
    ): TvShowRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { tvShowId ->
                appDatabase.tvShowRemoteKeysDao().remoteKeysTVShowId(tvShowId)
            }
        }
    }
}