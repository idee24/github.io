package com.bookitng.bookitng.networking.services

import com.bookitng.bookitng.models.BusesForRoute
import com.bookitng.bookitng.networking.Routes
import retrofit2.Call
import retrofit2.http.*

interface BusService {

    @GET
    fun getBusesForRoute(@Url url: String,
                         @Header("Authorization") bearerToken: String?,
                         @Query("limit") limit: Int,
                         @Query("page") page: Int): Call<BusesForRoute>
}