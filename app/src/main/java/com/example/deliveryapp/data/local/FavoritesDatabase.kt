package com.example.deliveryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliveryapp.data.model.restaurant.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class FavoritesDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoritesDatabase? = null

        fun getInstance(context: Context): FavoritesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "favorites-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}