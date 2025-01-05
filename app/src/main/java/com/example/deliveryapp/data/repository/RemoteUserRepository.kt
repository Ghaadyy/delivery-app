package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.data.service.UserService
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteUserRepository : UserRepository {

    private suspend fun <T> callUserService(call: suspend () -> Response<T>): Result<T> {
        return kotlin.runCatching { call() }
            .fold(
                onSuccess = { res ->
                    if (res.isSuccessful) Result.success(res.body()!!)
                    else Result.failure(Exception(res.message()))
                },
                onFailure = { e -> Result.failure(e) }
            )
    }

    override suspend fun fetchUserInfo(): Result<User> {
        return callUserService { service.getUserInfo() }
    }

    override suspend fun login(user: User): Result<TokenResponse> {
        return callUserService { service.login(user) }
    }

    override suspend fun signup(user: User): Result<TokenResponse> {
        return callUserService { service.signup(user) }
    }

    override suspend fun update(user: User): Result<User> {
        return callUserService { service.update(user) }
    }

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5299/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: UserService = retrofit.create(UserService::class.java)
    }
}