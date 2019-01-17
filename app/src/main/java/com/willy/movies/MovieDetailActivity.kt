package com.willy.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.widget.Toast
import com.willy.movies.adapter.ActorAdapter
import com.willy.movies.entity.MovieDetail
import com.willy.movies.presentation.MovieDetailContract
import kotlinx.android.synthetic.main.activity_moviedetail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
  companion object {
    const val EXTRA_MOVIENAME = "com.willy.movies.EXTRA_MOVIENAME"
  }

  lateinit var presenter: MovieDetailContract.Presenter
  lateinit var actorAdapter: ActorAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_moviedetail)
    Injector.injector.inject(this)

    actorAdapter = ActorAdapter(this, arrayListOf())
    recyclerView.run {
      layoutManager = GridLayoutManager(this@MovieDetailActivity, 2)
      adapter = actorAdapter
    }
  }

  override fun onResume() {
    super.onResume()
    presenter.attach(this)
    val movieName = intent.getStringExtra(EXTRA_MOVIENAME)
    presenter.reloadMovieDetail(movieName)
  }

  override fun onPause() {
    super.onPause()
    presenter.detach()
  }

  override fun showLoading(isLoading: Boolean) {
    runOnUiThread {
      loadingPane.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
  }

  override fun showMessage(message: String) {
    runOnUiThread {
      Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }
  }

  override fun displayMovieDetail(movieDetail: MovieDetail) {
    runOnUiThread {
      descriptionText.text = movieDetail.description
      title = movieDetail.name
      rating.rating = movieDetail.score.let { it / 2 }.toFloat()

      actorAdapter.run {
        data.clear()
        data.addAll(movieDetail.actors)
        notifyDataSetChanged()
      }
    }
  }

}
