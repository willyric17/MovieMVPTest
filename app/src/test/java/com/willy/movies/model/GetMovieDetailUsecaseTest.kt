package com.willy.movies.model

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.willy.movies.Injector
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMovieDetailUsecaseTest() {
  lateinit var usecase: GetMovieDetailUsecase
  lateinit var mockrepo: INativeRepository

  @Before
  fun setup() {
    mockrepo = mock()

    Injector.injector = object : Injector() {
      override fun inject(usecase: GetMovieDetailUsecase) {
        usecase.apply {
          repository = mockrepo
          scheduler = Schedulers.trampoline()
        }

      }
    }
    usecase = GetMovieDetailUsecase()
  }

  @Test
  fun `usecase error when repo give empty string`() {
    whenever(mockrepo.getMovieDetail("Top Gun 0")).thenReturn("")
    usecase.execute("Top Gun 0").test()
        .assertError { it is JSONException }
  }

  @Test
  fun `usecase error when repo give invalid json string`() {
    val invalidJSON =
        """{"name":"Top Gun 0","detail":"As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.","score":3.000000,"actors":[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]}"""
    whenever(mockrepo.getMovieDetail("Top Gun 0")).thenReturn(invalidJSON)
    usecase.execute("Top Gun 0").test()
        .assertError { it is JSONException }
  }

  @Test
  fun test_returnMovieList_whenRepositoryMovieJson() {
    val json =
        """{"name":"Top Gun 0","description":"As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom.","score":3.000000,"actors":[{"name":"Tom Cruise","age":50,"imageUrl":""},{"name":"Val Kilmer","age":46,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTk3ODIzMDA5Ml5BMl5BanBnXkFtZTcwNDY0NTU4Ng@@._V1_UY317_CR4,0,214,317_AL_.jpg"},{"name":"Tim Robbins","age":55,"imageUrl":"https://m.media-amazon.com/images/M/MV5BMTI1OTYxNzAxOF5BMl5BanBnXkFtZTYwNTE5ODI4._V1_UY317_CR16,0,214,317_AL_.jpg"}]}"""
    whenever(mockrepo.getMovieDetail("Top Gun 0")).thenReturn(json)
    usecase.execute("Top Gun 0").test()
        .assertNoErrors()
        .assertValue { detail ->
          Assert.assertEquals("Top Gun 0", detail.name)
          val expectedDescription =
              """As students at the United States Navy's elite fighter weapons school compete to be best in the class, one daring young pilot learns a few things from a civilian instructor that are not taught in the classroom."""
          Assert.assertEquals(expectedDescription, detail.description)
          Assert.assertTrue("Movie Score does not same", 3.0 == detail.score)
          true
        }
  }
}