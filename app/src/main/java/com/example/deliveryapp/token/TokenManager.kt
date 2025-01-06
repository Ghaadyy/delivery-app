package com.example.deliveryapp.token

import android.content.Context
import android.content.SharedPreferences
import com.auth0.android.jwt.JWT

object TokenManager {
    private const val PREFERENCE = "authPrefs"
    private const val TOKEN_IDENTIFIER = "token"
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
    }

    fun saveToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_IDENTIFIER, token).apply()
    }

    fun retrieveToken(): String? {
        return sharedPreferences.getString(TOKEN_IDENTIFIER, null)
    }

    fun clearToken() {
        sharedPreferences.edit().remove(TOKEN_IDENTIFIER).apply()
    }

    fun isTokenNotValid(): Boolean {
        return try {
            val token = retrieveToken() ?: return true
            val jwt = JWT(token)
            val expirationTime = jwt.expiresAt
            expirationTime != null && expirationTime.before(java.util.Date())
        } catch (e: Exception) {
            true
        }
    }
}