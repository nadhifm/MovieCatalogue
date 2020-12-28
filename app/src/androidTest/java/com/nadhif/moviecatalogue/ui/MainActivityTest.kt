package com.nadhif.moviecatalogue.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.nadhif.moviecatalogue.R
import com.nadhif.moviecatalogue.utils.DataDummy
import com.nadhif.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    private val dummyMovie = DataDummy.generateDummyRemoteMovie()
    private val dummyTv = DataDummy.generateDummyRemoteTvShow()

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun loadMovie() {
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovie)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
    }

    @Test
    fun loadDetailMovie() {
        onView(withText(dummyMovie[0].title)).perform(click())
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText(dummyMovie[0].releaseDate)))
    }

    @Test
    fun loadFavoriteMovie() {
        onView(withText(dummyMovie[0].title)).perform(click())
        onView(withId(R.id.fabFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rvFavoriteMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFavoriteMovie)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(dummyMovie[0].title)))
        onView(withId(R.id.fabFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withText(dummyMovie[0].title)).check(doesNotExist())
    }

    @Test
    fun loadTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rvTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(withText(dummyTv[0].name)).perform(click())
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(dummyTv[0].name)))
        onView(withId(R.id.tvReleaseDate)).check(matches(isDisplayed()))
        onView(withId(R.id.tvReleaseDate)).check(matches(withText(dummyTv[0].firstAirDate)))
    }

    @Test
    fun loadFavoriteTvShow() {
        onView(withText("Tv Shows")).perform(click())
        onView(withText(dummyTv[0].name)).perform(click())
        onView(withId(R.id.fabFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withId(R.id.favoriteFragment)).perform(click())
        onView(withId(R.id.rvFavoriteMovie)).check(matches(isDisplayed()))
        onView(withText("Tv Shows")).perform(click())
        onView(withId(R.id.rvFavoriteTvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rvFavoriteTvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.tvTitleDetail)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleDetail)).check(matches(withText(dummyTv[0].name)))
        onView(withId(R.id.fabFavorite)).check(matches(isDisplayed()))
        onView(withId(R.id.fabFavorite)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
        onView(withText(dummyTv[0].name)).check(doesNotExist())
    }
}