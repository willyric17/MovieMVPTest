package com.willy.movies.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.willy.movies.R
import com.willy.movies.entity.Actor
import kotlinx.android.synthetic.main.item_actor.view.actorName
import kotlinx.android.synthetic.main.item_actor.view.actorPhoto
import java.util.ArrayList

class ActorAdapter(
  val context: Context,
  val data: ArrayList<Actor>
) : RecyclerView.Adapter<ActorViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
    val view = LayoutInflater.from(context).inflate(R.layout.item_actor, parent, false)
    return ActorViewHolder(view)
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
    data[position].also { actor ->
      holder.itemView.apply {
        Glide.with(context).load(actor.imageUrl).into(actorPhoto)
        actorName.text = "${actor.name} (${actor.age})"
      }
    }
  }

}

class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view)