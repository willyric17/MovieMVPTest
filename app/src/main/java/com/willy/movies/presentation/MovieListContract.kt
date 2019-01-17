package com.willy.movies.presentation

import com.willy.movies.entity.Movie

interface MovieListContract {
  interface Presenter : BasePresenter<View> {
    fun refreshMovieList()
  }

  interface View {
    fun setLoading(loading: Boolean)
    fun showMessage(message: String)
    fun showMovieList(movies: List<Movie>)
  }
}