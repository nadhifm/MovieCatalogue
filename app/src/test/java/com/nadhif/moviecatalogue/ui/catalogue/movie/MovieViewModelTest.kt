package com.nadhif.moviecatalogue.ui.catalogue.movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MovieViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val movieResponse = DataDummy.generateDummyRemoteMovie()
    private lateinit var viewModel: MovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val catalogueRepository = mock(CatalogueRepository::class.java)

    @Before
    fun setup() {
        viewModel = MovieViewModel(catalogueRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getMovies() {
        val flow = flowOf(PagingData.from(movieResponse))
        `when`(catalogueRepository.getMovies()).thenReturn(flow)
        val pagingData = viewModel.getMovies()
        verify(catalogueRepository).getMovies()
        runBlocking {
            launch(Dispatchers.Main) {
                pagingData.collectLatest {
                    assertNotNull(it)
                }
            }
        }
    }
}