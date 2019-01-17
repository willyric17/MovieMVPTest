package com.willy.movies

import android.app.Application
import com.bumptech.glide.Glide
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class MovieApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    System.loadLibrary("media")

    Injector.injector = MovieApplicationInjector()
    Single.fromCallable {
      Glide.get(this).clearDiskCache()
    }.subscribeOn(Schedulers.io())
        .subscribe()
  }
}