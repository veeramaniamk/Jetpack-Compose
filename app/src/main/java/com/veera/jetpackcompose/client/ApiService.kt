package com.veera.jetpackcompose.client

import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Query("bioId")userId: Int, @Query("password") password: String): Response<Map<String, Any>>
}
