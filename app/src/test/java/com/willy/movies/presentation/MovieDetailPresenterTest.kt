package com.willy.movies.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.willy.movies.Injector
import com.willy.movies.entity.MovieDetail
import com.willy.movies.model.IGetMovieDetailUsecase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class MovieDetailPresenterTest {
  lateinit var presenter: MovieDetailPresenter
  lateinit var viewmock: MovieDetailContract.View
  lateinit var usecasemock: IGetMovieDetailUsecase

  @Before
  fun setup() {
    viewmock = mock()
    usecasemock = mock()

    Injector.injector = object : Injector() {
      override fun inject(presenter: MovieDetailPresenter) {
        presenter.apply {
          getMovieDetailUsecase = usecasemock
          scheduler = Schedulers.trampoline()
        }

      }
    }

    presenter = MovieDetailPresenter()
    presenter.attach(viewmock)
  }

  @Test
  fun `show message when movie detail is invalid`() {
    whenever(usecasemock.execute("movie")).thenReturn(Single.fromCallable { MovieDetail.parse("") })

    presenter.reloadMovieDetail("movie")

    verify(viewmock).showLoading(true)
    verify(viewmock).showLoading(false)
    verify(viewmock).showMessage("Failed to load Detail")
  }

  @Test
  fun `show movie detail when data is ready`() {
    val detail = MovieDetail("name", "description", 4.0)
    whenever(usecasemock.execute("name")).thenReturn(Single.just(detail))

    presenter.reloadMovieDetail("name")

    verify(viewmock).showLoading(true)
    verify(viewmock).showLoading(false)
    verify(viewmock).displayMovieDetail(detail)
  }

}