package com.example.deliveryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.deliveryapp.data.model.MealOptionConverters
import com.example.deliveryapp.data.model.MealRequest
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.restaurant.Favorite
import com.example.deliveryapp.data.model.restaurant.Review

@Database(entities = [Favorite::class, Review::class, Order::class, MealRequest::class], version = 14)
@TypeConverters(MealOptionConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun reviewDao(): ReviewDao
    abstract fun orderDao(): OrderDao
    abstract fun mealRequestDao(): MealRequestDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app-db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}