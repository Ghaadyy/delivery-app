package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User

interface UserRepository {
    suspend fun fetchUserInfo(): Result<User>
    suspend fun login(user: User): Result<TokenResponse>
    suspend fun signup(user: User): Result<TokenResponse>
    suspend fun update(user: User): Result<User>
}