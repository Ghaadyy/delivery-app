package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.local.AppDatabase
import com.example.deliveryapp.data.model.restaurant.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesRepository(private val context: Context) {
    suspend fun getFavoriteRestaurants(): List<Favorite> = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val favoriteDao = instance.favoriteDao()
        val favorites = favoriteDao.getFavoriteRestaurants()
        favorites
    }

    suspend fun likeRestaurant(favorite: Favorite) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val favoriteDao = instance.favoriteDao()
        favoriteDao.likeRestaurant(favorite)
    }

    suspend fun dislikeRestaurant(favorite: Favorite) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val favoriteDao = instance.favoriteDao()
        favoriteDao.dislikeRestaurant(favorite)
    }
}