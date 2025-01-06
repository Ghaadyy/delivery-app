package com.example.deliveryapp.token

import android.content.Context
import android.content.Intent
import com.example.deliveryapp.AuthenticationActivity
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.toString().contains("login") || request.url.toString().contains("signup")) {
            return chain.proceed(request)
        }
        val token = TokenManager.retrieveToken()

        return if(!TokenManager.isTokenNotValid()){
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()

            chain.proceed(newRequest)
        } else {
            TokenManager.clearToken()
            redirectToAuthActivity()
            chain.proceed(request)
        }
    }

    private fun redirectToAuthActivity() {
        val intent = Intent(context, AuthenticationActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
    }
}