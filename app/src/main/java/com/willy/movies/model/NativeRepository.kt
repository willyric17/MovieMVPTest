package com.willy.movies.model

interface INativeRepository {
    fun getMovieData(): String
    fun getMovieDetail(movieName: String): String
}

class NativeRepository : INativeRepository {
    external override fun getMovieData(): String
    external override fun getMovieDetail(movieName: String): String
}