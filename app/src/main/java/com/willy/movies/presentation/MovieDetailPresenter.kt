package com.willy.movies.presentation

import com.willy.movies.Injector
import com.willy.movies.model.IGetMovieDetailUsecase
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class MovieDetailPresenter : MovieDetailContract.Presenter {
  lateinit var getMovieDetailUsecase: IGetMovieDetailUsecase
  lateinit var scheduler: Scheduler
  val disposables = CompositeDisposable()
  var view: MovieDetailContract.View? = null

  init {
    Injector.injector.inject(this)
  }

  override fun reloadMovieDetail(movieName: String) {
    view?.showLoading(true)
    getMovieDetailUsecase.execute(movieName)
        .observeOn(scheduler)
        .subscribe(
            {
              view?.run {
                showLoading(false)
                displayMovieDetail(it)
              }
            },
            {
              view?.run {
                showLoading(false)
                showMessage("Failed to load Detail")
              }
            }
        ).also { disposables.add(it) }
  }

  override fun attach(view: MovieDetailContract.View) {
    this.view = view
  }

  override fun detach() {
    this.view = null
    disposables.clear()
  }
}