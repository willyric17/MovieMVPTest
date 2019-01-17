package com.willy

import com.willy.movies.MainActivity
import com.willy.movies.model.GetMoviesUsecase
import com.willy.movies.model.NativeRepository
import com.willy.movies.presentation.MovieListPresenter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

abstract class Injector {
    companion object {
        lateinit var injector: Injector
    }

    open fun inject(usecase: GetMoviesUsecase) {}
    open fun inject(presenter: MovieListPresenter) {}
    open fun inject(activity: MainActivity) {}
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
}

