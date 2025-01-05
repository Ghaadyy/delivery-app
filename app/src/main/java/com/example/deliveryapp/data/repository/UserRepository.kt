package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.JsonPatch
import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User

interface UserRepository {
    suspend fun fetchUserInfo(token: String): Result<User>
    suspend fun login(user: User): Result<TokenResponse>
    suspend fun signup(user: User): Result<TokenResponse>
    suspend fun update(token: String, patch: List<JsonPatch>): Result<User>
}