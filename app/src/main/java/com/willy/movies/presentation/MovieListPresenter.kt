package com.willy.movies.presentation

import com.willy.Injector
import com.willy.movies.model.IGetMoviesUsecase
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

class MovieListPresenter : MovieList.Presenter {
    lateinit var getMovieListUsecase: IGetMoviesUsecase
    lateinit var scheduler: Scheduler
    val disposables = CompositeDisposable()
    var view: MovieList.View? = null

    init {
        Injector.injector.inject(this)
    }

    override fun refreshMovieList() {
        view?.setLoading(true)
        getMovieListUsecase.execute()
            .observeOn(scheduler)
            .subscribe({
                view?.setLoading(false)
                when (it.size) {
                    0 -> view?.showMessage("No movie available")
                    else -> view?.showMovieList(it)
                }
            }, {})
            .also { disposables.add(it) }
    }

    override fun attach(view: MovieList.View) {
        this.view = view
    }

    override fun detach() {
        view = null
        disposables.clear()
    }

}