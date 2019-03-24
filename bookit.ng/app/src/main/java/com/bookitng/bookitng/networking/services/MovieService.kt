package com.bookitng.bookitng.networking.services

import com.bookitng.bookitng.models.TodayMovies
import com.bookitng.bookitng.networking.Routes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieService {

    @GET(Routes.TODAY_MOVIES_ENDPOINT)
    fun retrieveTodayMovies(@Header("Authorization") bearerToken: String?,
                            @Query("limit") limit: Int,
                            @Query("page") page: Int): Call<TodayMovies>
}