package com.willy.movies.entity

import org.json.JSONObject

data class MovieDetail(val name: String, val description: String, val score: Double) {
  val actors = mutableListOf<Actor>()

  companion object {
    fun parse(jsonString: String): MovieDetail {
      return MovieDetail.parse(JSONObject(jsonString))
    }

    fun parse(json: JSONObject): MovieDetail {
      return MovieDetail(json.getString("name"), json.getString("description"),
          json.getDouble("score")).apply {
        actors.addAll(Actor.parseArray(json.getJSONArray("actors")))
      }
    }
  }
}