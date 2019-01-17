package com.willy.movies.entity

import org.json.JSONArray
import org.json.JSONObject

data class Actor(val name: String, val age: Int, val imageUrl: String) {
  companion object {
    fun parseArray(array: JSONArray): List<Actor> {
      return (0 until array.length()).map { parse(array.getJSONObject(it)) }
    }

    fun parse(json: JSONObject): Actor {
      return Actor(json.getString("name"), json.getInt("age"), json.getString("imageUrl"))
    }
  }
}