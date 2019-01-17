package com.willy.movies.model

import com.willy.Injector
import com.willy.movies.entity.Movie
import io.reactivex.Scheduler
import io.reactivex.Single

interface IGetMoviesUsecase {
    fun execute(): Single<List<Movie>>
}

class GetMoviesUsecase() : IGetMoviesUsecase {
    lateinit var repository: INativeRepository
    lateinit var scheduler: Scheduler

    init {
        Injector.injector.inject(this)
    }

    override fun execute(): Single<List<Movie>> =
        Single.fromCallable {
            repository.getMovieData()
        }.subscribeOn(scheduler)
            .map { Movie.parseArray(it) }
            .onErrorReturn { emptyList() }

}