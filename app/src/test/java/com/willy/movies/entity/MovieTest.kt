package com.willy.movies.entity

import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class MovieTest {
    @Test(expected = JSONException::class)
    fun `on parse empty json, throws exception`() {
        Movie.parse("")
    }

    @Test(expected = JSONException::class)
    fun `on parse invalid json string, throws exception`() {
        val json = """{"name":"Top Gun 0","firstUpdated":0}"""
        Movie.parse(json)
    }

    @Test(expected = JSONException::class)
    fun `on parse invalid json object, throws exception`() {
        val json = """{"name":"Top Gun 0","firstUpdated":0}"""
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(json)
        } catch (e: JSONException) {
            fail()
        }
        Movie.parse(jsonObject!!)
    }

    @Test
    fun `extract name and lastupdated from json string`() {
        val json = """{"name":"Top Gun 0","lastUpdated":0}"""
        val movie = Movie.parse(json)
        assertEquals("Top Gun 0", movie.name)
        assertEquals(0, movie.lastUpdated)
    }

    @Test
    fun `extract name and lastupdated from json object`() {
        val json = """{"name":"Top Gun 0","lastUpdated":0}"""
        var jsonObject: JSONObject? = null
        try {
            jsonObject = JSONObject(json)
        } catch (e: JSONException) {
            fail()
        }
        val movie = Movie.parse(jsonObject!!)
        assertEquals("Top Gun 0", movie.name)
        assertEquals(0, movie.lastUpdated)
    }
}