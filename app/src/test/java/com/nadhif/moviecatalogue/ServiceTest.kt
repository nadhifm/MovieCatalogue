package com.nadhif.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.nadhif.moviecatalogue.network.ApiService
import com.nadhif.moviecatalogue.utils.Constants
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ServiceTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")
    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mockWebServer = MockWebServer()

        val retrofit= Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

       service = retrofit.create(ApiService::class.java)

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun getPopularMovie() {
        runBlocking {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                enqueueResponse("popular-movie.json")
                val movie = service.getPopularMovieAsync(page = 1).await()
                assertNotNull(movie)
                assertEquals(movie.page, 1)
                assertEquals(movie.results[0].backdropPath, "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg")
            }
        }
    }

    @Test
    fun getPopularTvShow() {
        runBlocking {
            launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
                enqueueResponse("popular-tv-show.json")
                val tvShow = service.getPopularTvShowAsync(page = 1).await()
                assertNotNull(tvShow)
                assertEquals(tvShow.page, 1)
                assertEquals(tvShow.results[0].backdropPath, "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg")
            }
        }
    }

    private fun enqueueResponse(filename: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("api-response/$filename")
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }

        mockWebServer.enqueue(mockResponse.setBody(source.readString(Charsets.UTF_8)))
    }
}