package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.local.AppDatabase
import com.example.deliveryapp.data.model.MealRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CartRepository(private val context: Context) {
    suspend fun getCart(): List<MealRequest> = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val mealRequestDao = instance.mealRequestDao()
        val cart = mealRequestDao.getCart()
        cart
    }

    suspend fun addToCart(mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val mealRequestDao = instance.mealRequestDao()
        mealRequestDao.addToCart(mealRequest)
    }

    suspend fun updateItem(mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val mealRequestDao = instance.mealRequestDao()
        mealRequestDao.updateItem(mealRequest)
    }

    suspend fun removeFromCart(mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val mealRequestDao = instance.mealRequestDao()
        mealRequestDao.removeFromCart(mealRequest)
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        val instance = AppDatabase.getInstance(context)
        val mealRequestDao = instance.mealRequestDao()
        mealRequestDao.clearCart()
    }
}