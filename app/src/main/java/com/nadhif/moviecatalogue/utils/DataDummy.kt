package com.nadhif.moviecatalogue.utils

import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteMovie
import com.nadhif.moviecatalogue.data.source.local.entity.FavoriteTvShow
import com.nadhif.moviecatalogue.data.source.remote.response.Movie
import com.nadhif.moviecatalogue.data.source.remote.response.TvShow

object DataDummy {
    fun generateDummyRemoteMovie(): List<Movie> {
        val listMovie = arrayListOf<Movie>()
        listMovie.add(
                Movie(
                        0,
                        "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
                        590706,
                        "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
                        "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                        "2020-11-20",
                        "Jiu Jitsu"
                )
        )
        return listMovie
    }

    fun generateDummyRemoteTvShow(): List<TvShow> {
        val listTvShow = arrayListOf<TvShow>()
        listTvShow.add(
            TvShow(
                0,
                82856,
                "2019-11-12",
                "The Mandalorian",
                "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg"
            )
        )
        return listTvShow
    }

    fun generateDummyFavoriteTvShow(): List<FavoriteTvShow> {
        val listTvShow = arrayListOf<FavoriteTvShow>()
        listTvShow.add(
                FavoriteTvShow(
                        82856,
                        "2019-11-12",
                        "The Mandalorian",
                        "After the fall of the Galactic Empire, lawlessness has spread throughout the galaxy. A lone gunfighter makes his way through the outer reaches, earning his keep as a bounty hunter.",
                        "/sWgBv7LV2PRoQgkxwlibdGXKz1S.jpg",
                        "/9ijMGlJKqcslswWUzTEwScm82Gs.jpg"
                )
        )
        return listTvShow
    }

    fun generateDummyFavoriteMovie(): List<FavoriteMovie> {
        val listMovie = arrayListOf<FavoriteMovie>()
        listMovie.add(
            FavoriteMovie(
                "/jeAQdDX9nguP6YOX6QSWKDPkbBo.jpg",
                590706,
                "Every six years, an ancient order of jiu-jitsu fighters joins forces to battle a vicious race of alien invaders. But when a celebrated war hero goes down in defeat, the fate of the planet and mankind hangs in the balance.",
                "/eLT8Cu357VOwBVTitkmlDEg32Fs.jpg",
                "2020-11-20",
                "Jiu Jitsu"
            )
        )
        return listMovie
    }

}