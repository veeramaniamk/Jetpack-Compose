package com.veera.jetpackcompose.client

import retrofit2.http.GET

interface ApiService {
    @GET("posts")
    suspend fun getPosts(): Map<String, Any>
}
