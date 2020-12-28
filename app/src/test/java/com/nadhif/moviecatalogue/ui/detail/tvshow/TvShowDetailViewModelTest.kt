package com.nadhif.moviecatalogue.ui.detail.tvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nadhif.moviecatalogue.data.CatalogueRepository
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow
import com.nadhif.moviecatalogue.utils.DataDummy
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class TvShowDetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyTvShow = DataDummy.generateDummyRemoteTvShow()[0]
    private lateinit var viewModel: TvShowDetailViewModel

    private val catalogueRepository = Mockito.mock(CatalogueRepository::class.java)

    @Suppress("UNCHECKED_CAST")
    private val observer = Mockito.mock(Observer::class.java) as Observer<TvShow>

    @Before
    fun setup() {
        viewModel = TvShowDetailViewModel(catalogueRepository, dummyTvShow)
    }

    @Test
    fun getSelectedTvShow() {
        viewModel.selectedTvShow.observeForever(observer)
        Mockito.verify(observer).onChanged(dummyTvShow)
    }
}