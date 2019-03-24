package com.bookitng.bookitng.networking.services

import com.bookitng.bookitng.models.LoginModel
import com.bookitng.bookitng.models.LoginPayload
import com.bookitng.bookitng.networking.Routes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST(Routes.LOGIN_ENDPOINT)
    fun initiateLogin(@Body loginModel: LoginModel): Call<LoginPayload>
}