package com.willy.movies.entity

import org.json.JSONException
import org.json.JSONObject
import org.junit.Assert.*
import org.junit.Test

class MovieDetailTest {
  @Test(expected = JSONException::class)
  fun `throw JSONException on parse empty string`() {
    val string = """"""
    MovieDetail.parse(string)
  }

  @Test(expected = JSONException::class)
  fun `throw JSONException on parse invalid string`() {
    val string =
        """{"name":"Top Gun 0",'detail":"As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.","score":3.000000,"actors":[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]}"""
    MovieDetail.parse(string)
  }

  @Test
  fun `extract name, description, score, and actors on string`() {
    val string =
        """{"name":"Top Gun 0","description":"As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.","score":3.000000,"actors":[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]}"""
    val detail = MovieDetail.parse(string)

    with(detail) {
      assertEquals("Top Gun 0", name)
      val expectedDescription =
          """As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom."""
      assertEquals(expectedDescription, description)
      assertTrue("Score does not match", 3.0 == score)
      assertTrue("Actor count does not match", 3 == actors.size)

      assertEquals("Tom Cruise", actors[0].name)
      assertEquals("Val Kilmer", actors[1].name)
      assertEquals("Tim Robbins", actors[2].name)
    }
  }

  @Test
  fun `extract name, description, score, and actors on json object`() {
    val string =
        """{"name":"Top Gun 0","description":"As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.","score":3.000000,"actors":[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]}"""
    val json = JSONObject(string)
    val detail = MovieDetail.parse(json)

    with(detail) {
      assertEquals("Top Gun 0", name)
      val expectedDescription =
          """As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom."""
      assertEquals(expectedDescription, description)
      assertTrue("Score does not match", 3.0 == score)
      assertTrue("Actor count does not match", 3 == actors.size)

      assertEquals("Tom Cruise", actors[0].name)
      assertEquals("Val Kilmer", actors[1].name)
      assertEquals("Tim Robbins", actors[2].name)
    }
  }
}