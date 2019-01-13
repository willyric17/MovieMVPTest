package com.willy.movies

import android.app.Application

class MovieApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    System.load("media")
  }
}