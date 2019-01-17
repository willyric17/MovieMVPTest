package com.willy.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.willy.movies.adapter.MovieAdapter
import com.willy.movies.entity.Movie
import com.willy.movies.presentation.MovieListContract
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieListContract.View {
  override fun showMessage(message: String) {
    runOnUiThread {
      Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
  }

  override fun showMovieList(movies: List<Movie>) {
    movieAdapter.data.clear()
    runOnUiThread {
      movieAdapter.data.addAll(movies)
      movieAdapter.notifyDataSetChanged()
    }
  }

  override fun setLoading(loading: Boolean) {
    runOnUiThread {
      swipeRefresh.isRefreshing = loading
    }
  }

  lateinit var presenter: MovieListContract.Presenter
  lateinit var movieAdapter: MovieAdapter
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    Injector.injector.inject(this)

    swipeRefresh.setOnRefreshListener { presenter.refreshMovieList() }
    recyclerView.apply {
      movieAdapter = MovieAdapter(context, arrayListOf())
      adapter = movieAdapter
      layoutManager = LinearLayoutManager(context)
    }

  }

  override fun onResume() {
    super.onResume()
    presenter.attach(this)
    presenter.refreshMovieList()
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }
}