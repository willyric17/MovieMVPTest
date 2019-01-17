package com.willy.movies.entity

import org.json.JSONArray
import org.json.JSONObject

data class Movie(val name: String, val lastUpdated: Int) {
    companion object {
        fun parseArray(jsonArray: String): List<Movie> {
            val array = JSONArray(jsonArray)
            return (0 until array.length()).map {
                Movie.parse(array.getJSONObject(it))
            }
        }

        fun parse(json: String): Movie {
            val jsonObject = JSONObject(json)
            return parse(jsonObject)
        }

        fun parse(json: JSONObject): Movie {
            return Movie(json.getString("name"), json.getInt("lastUpdated"))
        }
    }
}