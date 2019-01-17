package com.willy.movies.model

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.willy.Injector
import com.willy.movies.entity.Movie
import io.reactivex.schedulers.Schedulers
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetMoviesUsecaseTest() {
    lateinit var usecase: GetMoviesUsecase
    lateinit var mockrepo: INativeRepository

    val json =
        """[{"name":"Top Gun 0","lastUpdated":0},{"name":"Top Gun 1","lastUpdated":10000},{"name":"Top Gun 2","lastUpdated":20000},{"name":"Top Gun 3","lastUpdated":30000},{"name":"Top Gun 4","lastUpdated":40000},{"name":"Top Gun 5","lastUpdated":50000},{"name":"Top Gun 6","lastUpdated":60000},{"name":"Top Gun 7","lastUpdated":70000},{"name":"Top Gun 8","lastUpdated":80000},{"name":"Top Gun 9","lastUpdated":90000}]"""

    @Before
    fun setup() {
        mockrepo = mock()

        Injector.injector = object : Injector() {
            override fun inject(getMoviesUsecase: GetMoviesUsecase) {
                getMoviesUsecase.apply {
                    repository = mockrepo
                    scheduler = Schedulers.trampoline()
                }

            }
        }
        usecase = GetMoviesUsecase()
    }

    @Test
    fun test_returnEmpty_whenRepositoryEmptyString() {
        whenever(mockrepo.getMovieData()).thenReturn("")
        usecase.execute().test()
            .assertValue { it.equals(emptyList<Movie>()) }
            .assertNoErrors()
    }

    @Test
    fun test_returnMovieList_whenRepositoryMovieJson() {
        whenever(mockrepo.getMovieData()).thenReturn(json)
        usecase.execute().test()
            .assertNoErrors()
            .assertValue { movies ->
                Assert.assertEquals(10, movies.size)
                (movies.indices).forEach {
                    Assert.assertEquals("Top Gun $it", movies[it].name)
                    Assert.assertEquals(it * 10000, movies[it].lastUpdated)
                }
                true
            }
    }
}