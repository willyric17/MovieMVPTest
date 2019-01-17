package com.willy.movies.presentation

import com.willy.movies.entity.MovieDetail

interface MovieDetailContract {
  interface Presenter : BasePresenter<MovieDetailContract.View> {
    fun reloadMovieDetail(movieName: String)
  }

  interface View {
    fun showLoading(isLoading: Boolean)
    fun showMessage(message: String)
    fun displayMovieDetail(movieDetail: MovieDetail)
  }
}