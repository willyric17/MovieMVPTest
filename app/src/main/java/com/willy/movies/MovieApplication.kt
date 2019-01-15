package com.willy.movies

import android.app.Application
import android.util.Log
import com.willy.movies.model.NativeRepository

class MovieApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    System.loadLibrary("media")

    val repository = NativeRepository()
    Log.d("json", repository.getMovieData())
    Log.d("json", repository.getMovieDetailData())
  }
}