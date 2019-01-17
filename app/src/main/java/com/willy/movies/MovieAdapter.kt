package com.willy.movies

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.willy.movies.entity.Movie
import kotlinx.android.synthetic.main.item_movie.view.*
import java.text.SimpleDateFormat
import java.util.*

class MovieAdapter(val context: Context, val data: ArrayList<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    val dateformat by lazy { SimpleDateFormat("dd/MM/yyyy") }
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        data[position].also { movie ->
            holder.itemView.apply {
                itemName.text = movie.name
                itemDate.text = dateformat.format(Date(movie.lastUpdated.toLong()))
                card.setOnClickListener {  }
            }
        }
    }

}

class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)