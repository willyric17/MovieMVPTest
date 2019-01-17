package com.willy.movies.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.willy.movies.MovieDetailActivity
import com.willy.movies.R
import com.willy.movies.entity.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter(
  val context: Context,
  val data: ArrayList<Movie>
) : RecyclerView.Adapter<MovieViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
    return MovieViewHolder(view)
  }

  override fun getItemCount(): Int = data.size

  val dateformat by lazy { SimpleDateFormat.getDateInstance() }
  override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
    data[position].also { movie ->
      holder.itemView.apply {
        itemName.text = movie.name
        itemDate.text = dateformat.format(Date(movie.lastUpdated.toLong()))
        card.setOnClickListener {
          context.startActivity(Intent(context, MovieDetailActivity::class.java).apply {
            putExtra(MovieDetailActivity.EXTRA_MOVIENAME, movie.name)
          })
        }
      }
    }
  }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)