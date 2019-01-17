package com.willy.movies

import com.willy.movies.model.GetMovieDetailUsecase
import com.willy.movies.model.GetMoviesUsecase
import com.willy.movies.model.NativeRepository
import com.willy.movies.presentation.MovieDetailPresenter
import com.willy.movies.presentation.MovieListPresenter
import io.reactivex.schedulers.Schedulers

abstract class Injector {
  companion object {
    lateinit var injector: Injector
  }

  open fun inject(usecase: GetMoviesUsecase) {}
  open fun inject(presenter: MovieListPresenter) {}
  open fun inject(activity: MainActivity) {}

  open fun inject(usecase: GetMovieDetailUsecase) {}
  open fun inject(presenter: MovieDetailPresenter) {}
  open fun inject(activity: MovieDetailActivity) {}
}

class MovieApplicationInjector : Injector() {
  override fun inject(usecase: GetMoviesUsecase) {
    usecase.repository = NativeRepository()
    usecase.scheduler = Schedulers.io()
  }

  override fun inject(presenter: MovieListPresenter) {
    presenter.getMovieListUsecase = GetMoviesUsecase()
    presenter.scheduler = Schedulers.computation()
  }

  override fun inject(activity: MainActivity) {
    activity.presenter = MovieListPresenter()
  }

  override fun inject(usecase: GetMovieDetailUsecase) {
    usecase.repository = NativeRepository()
    usecase.scheduler = Schedulers.io()
  }

  override fun inject(presenter: MovieDetailPresenter) {
    presenter.getMovieDetailUsecase = GetMovieDetailUsecase()
    presenter.scheduler = Schedulers.computation()
  }

  override fun inject(activity: MovieDetailActivity) {
    activity.presenter = MovieDetailPresenter()
  }
}

