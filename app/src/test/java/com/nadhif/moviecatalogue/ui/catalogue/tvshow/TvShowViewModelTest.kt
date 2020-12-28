package com.nadhif.moviecatalogue.ui.catalogue.tvshow

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
import org.mockito.Mockito

class TvShowViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private val tvShowResponse = DataDummy.generateDummyRemoteTvShow()
    private lateinit var viewModel: TvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val catalogueRepository = Mockito.mock(CatalogueRepository::class.java)

    @Before
    fun setup() {
        viewModel = TvShowViewModel(catalogueRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getPopularTvShow() {
        val flow = flowOf(PagingData.from(tvShowResponse))
        Mockito.`when`(catalogueRepository.getPopularTvShow()).thenReturn(flow)
        val pagingData = viewModel.getPopularTvShow()
        verify(catalogueRepository).getPopularTvShow()
        runBlocking {
            launch(Dispatchers.Main) {
                pagingData.collectLatest {
                    assertNotNull(it)
                }
            }
        }
    }
}