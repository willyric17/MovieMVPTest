package com.willy.movies.presentation

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.willy.Injector
import com.willy.movies.entity.Movie
import com.willy.movies.model.IGetMoviesUsecase
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test

class MovieListPresenterTest {
    lateinit var presenter: MovieListPresenter
    lateinit var viewmock: MovieList.View
    lateinit var usecasemock: IGetMoviesUsecase

    @Before
    fun setup() {
        viewmock = mock()
        usecasemock = mock()

        Injector.injector = object : Injector() {
            override fun inject(presenter: MovieListPresenter) {
                presenter.apply {
                    getMovieListUsecase = usecasemock
                    scheduler = Schedulers.trampoline()
                }

            }
        }

        presenter = MovieListPresenter()
        presenter.attach(viewmock)
    }

    @Test
    fun `view show data as usecase return`() {
        val movielist = listOf(
            Movie("James Bond", 0),
            Movie("Johnny English", 1)
        )
        whenever(usecasemock.execute()).thenReturn(Single.just(movielist))

        presenter.refreshMovieList()

        verify(viewmock).setLoading(true)
        verify(viewmock).showMovieList(movielist)
        verify(viewmock).setLoading(false)
    }

    @Test
    fun `view show message when usecase return empty`() {
        val movielist = emptyList<Movie>()
        whenever(usecasemock.execute()).thenReturn(Single.just(movielist))

        presenter.refreshMovieList()
        verify(viewmock).setLoading(true)
        verify(viewmock, times(1)).showMessage("No movie available")
        verify(viewmock).setLoading(false)
    }

}