package com.willy.movies.entity

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class ActorTest {
    @Test(expected = JSONException::class)
    fun `throw JSONException when parsing invalid JSONArray`() {
        val string =
            """[{"name":"Tom Cruise","age":50,"image":""},{"name":"Val Kilmer","age":46,"image":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"image":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]"""
        lateinit var array: JSONArray
        try {
            array = JSONArray(string)
        } catch (ignored: JSONException) {
            fail()
        }
        Actor.parseArray(array)
    }

    @Test
    fun `extract name, age, and imageURL when parsing json`() {
        val string =
            """{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"}"""
        lateinit var json: JSONObject
        try {
            json = JSONObject(string)
        } catch (ignored: JSONException) {
            fail()
        }
        val actor = Actor.parse(json)

        with(actor) {
            assertEquals("Val Kilmer", name)
            assertTrue("Age does not same", 46 == age)
            assertEquals(
                "https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg",
                imageUrl
            )
        }
    }

    @Test
    fun `extract name, age, and imageURL when parsing JSONArray`() {
        val string =
            """[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]"""
        lateinit var array : JSONArray
        try {
            array = JSONArray(string)
        } catch (ignored: JSONException){
            fail()
        }
        val actors = Actor.parseArray(array)
        assertTrue("Actor count does not same", actors.size == 3)
        assertEquals("Tom Cruise", actors[0].name)
        assertEquals("Val Kilmer", actors[1].name)
        assertEquals("Tim Robbins", actors[2].name)
    }
}