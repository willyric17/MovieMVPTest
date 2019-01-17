package com.willy.movies

import android.app.Application
import android.util.Log
import com.willy.Injector
import com.willy.MovieApplicationInjector
import com.willy.movies.model.NativeRepository

class MovieApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    System.loadLibrary("media")

    Injector.injector = MovieApplicationInjector()
  }
}