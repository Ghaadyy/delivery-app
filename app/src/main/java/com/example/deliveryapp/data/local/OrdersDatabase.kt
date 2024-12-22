package com.example.deliveryapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deliveryapp.data.model.Order

@Database(entities = [Order::class], version = 1)
abstract class OrdersDatabase : RoomDatabase() {
    abstract fun orderDao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrdersDatabase? = null
        fun getInstance(context: Context): OrdersDatabase {
            context.deleteDatabase("orders-db")
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrdersDatabase::class.java,
                    "orders-db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}