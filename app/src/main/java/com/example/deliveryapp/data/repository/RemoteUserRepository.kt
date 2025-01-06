package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.model.Address
import com.example.deliveryapp.data.model.JsonPatch
import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.data.service.UserService
import com.example.deliveryapp.token.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteUserRepository(context: Context) : UserRepository {

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

    override suspend fun update(user: List<JsonPatch>): Result<User> {
        return callUserService { service.update(user) }
    }

    override suspend fun getAddresses(): Result<List<Address>> {
        return callUserService { service.getAddresses() }
    }

    override suspend fun addAddress(address: Address): Result<Unit> {
        return callUserService { service.addAddress(address) }
    }

    companion object {
        private fun getOkHttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(context))
                .build()
        }

        private fun getRetrofit(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5299/")
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private val service: UserService = getRetrofit(context).create(UserService::class.java)
}