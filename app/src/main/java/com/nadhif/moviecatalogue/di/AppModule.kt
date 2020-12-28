package com.nadhif.moviecatalogue.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nadhif.moviecatalogue.data.source.local.room.AppDatabase
import com.nadhif.moviecatalogue.network.ApiService
import com.nadhif.moviecatalogue.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMovieDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "catalogue.db"
    ).build()

    @Singleton
    @Provides
    fun provideMovieDao(database: AppDatabase) = database.movieDao()

    @Singleton
    @Provides
    fun provideRemoteKeysDao(database: AppDatabase) = database.remoteKeysDao()

    @Singleton
    @Provides
    fun provideTvShowDao(database: AppDatabase) = database.tvShowDao()

    @Singleton
    @Provides
    fun provideFavoriteMovieDao(database: AppDatabase) = database.favoriteMovieDao()

    @Singleton
    @Provides
    fun provideFavoriteTvShowDao(database: AppDatabase) = database.favoriteTvShowDao()
}