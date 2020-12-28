package com.nadhif.moviecatalogue.ui.detail.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyMovie = DataDummy.generateDummyRemoteMovie()[0]
    private lateinit var viewModel: DetailViewModel

    private val catalogueRepository = mock(CatalogueRepository::class.java)

    @Suppress("UNCHECKED_CAST")
    private val observer = mock(Observer::class.java) as Observer<Movie>

    @Before
    fun setup() {
        viewModel = DetailViewModel(catalogueRepository, dummyMovie)
    }

    @Test
    fun getSelectedMovie() {
        viewModel.selectedMovie.observeForever(observer)
        Mockito.verify(observer).onChanged(dummyMovie)
    }
}