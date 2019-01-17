package com.willy.movies.model

import com.willy.movies.Injector
import com.willy.movies.entity.MovieDetail
import io.reactivex.Scheduler
import io.reactivex.Single

interface IGetMovieDetailUsecase {
  fun execute(movieName: String): Single<MovieDetail>
}

class GetMovieDetailUsecase() : IGetMovieDetailUsecase {
  lateinit var repository: INativeRepository
  lateinit var scheduler: Scheduler

  init {
    Injector.injector.inject(this)
  }

  override fun execute(movieName: String): Single<MovieDetail> =
      Single.just(movieName)
          .subscribeOn(scheduler)
          .map { repository.getMovieDetail(it) }
          .map { MovieDetail.parse(it) }
}