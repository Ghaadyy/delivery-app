package com.example.deliveryapp.data.service

import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface UserService {
    @GET("users/me")
    suspend fun getUserInfo(): Response<User>

    @POST("users/login")
    suspend fun login(@Body user: User): Response<TokenResponse>

    @POST("users/signup")
    suspend fun signup(@Body user: User): Response<TokenResponse>

    @PATCH("users")
    suspend fun update(@Body user: User): Response<User>
}