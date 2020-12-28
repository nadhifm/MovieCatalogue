package com.nadhif.moviecatalogue.ui.favorite.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class FavoriteViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie = DataDummy.generateDummyFavoriteMovie()
    private val dummyTvShow = DataDummy.generateDummyFavoriteTvShow()
    private lateinit var viewModel: FavoriteViewModel

    private val catalogueRepository = Mockito.mock(CatalogueRepository::class.java)

    @Suppress("UNCHECKED_CAST")
    private val observerMovie = Mockito.mock(Observer::class.java) as Observer<List<FavoriteMovie>>
    @Suppress("UNCHECKED_CAST")
    private val observerTvShow = Mockito.mock(Observer::class.java) as Observer<List<FavoriteTvShow>>

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(catalogueRepository)
    }

    @Test
    fun getFavoriteMovie() {
        val favoriteMovie = MutableLiveData<List<FavoriteMovie>>()
        favoriteMovie.value = dummyMovie
        `when`(catalogueRepository.getAllFavoriteMovies()).thenReturn(favoriteMovie)
        val listFavoriteMovie = viewModel.getFavoriteMovie()
        verify(catalogueRepository).getAllFavoriteMovies()
        assertNotNull(listFavoriteMovie)
        assertEquals(dummyMovie.size, listFavoriteMovie.value?.size)

        viewModel.getFavoriteMovie().observeForever(observerMovie)
        verify(observerMovie).onChanged(favoriteMovie.value)
    }

    @Test
    fun getFavoriteTvShow() {
        val favoriteTvShow = MutableLiveData<List<FavoriteTvShow>>()
        favoriteTvShow.value = dummyTvShow
        `when`(catalogueRepository.getAllFavoriteTvShows()).thenReturn(favoriteTvShow)
        val listFavoriteTvShow = viewModel.getFavoriteTvShow()
        verify(catalogueRepository).getAllFavoriteTvShows()
        assertNotNull(listFavoriteTvShow)
        assertEquals(dummyMovie.size, listFavoriteTvShow.value?.size)

        viewModel.getFavoriteTvShow().observeForever(observerTvShow)
        verify(observerTvShow).onChanged(favoriteTvShow.value)
    }
}