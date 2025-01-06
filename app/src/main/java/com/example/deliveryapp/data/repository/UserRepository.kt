package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.Address
import com.example.deliveryapp.data.model.JsonPatch
import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User

interface UserRepository {
    suspend fun fetchUserInfo(): Result<User>
    suspend fun login(user: User): Result<TokenResponse>
    suspend fun signup(user: User): Result<TokenResponse>
    suspend fun update(patch: List<JsonPatch>): Result<User>
    suspend fun getAddresses(): Result<List<Address>>
    suspend fun addAddress(address: Address): Result<Unit>
}