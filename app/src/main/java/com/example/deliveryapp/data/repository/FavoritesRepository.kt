package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.local.FavoritesDatabase
import com.example.deliveryapp.data.model.restaurant.Favorite

class FavoritesRepository {
    fun getFavoriteRestaurants(context: Context): List<Favorite> {
        val instance = FavoritesDatabase.getInstance(context)
        val favoriteDao = instance.favoriteDao()
        val favorites = favoriteDao.getFavoriteRestaurants()
        return favorites
    }
}