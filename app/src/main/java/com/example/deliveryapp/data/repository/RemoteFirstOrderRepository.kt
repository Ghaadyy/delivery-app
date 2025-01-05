package com.example.deliveryapp.data.repository

import android.content.Context
import android.util.Log
import com.example.deliveryapp.data.local.AppDatabase
import com.example.deliveryapp.data.local.OrderDao
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest
import com.example.deliveryapp.data.service.OrderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteFirstOrderRepository(context: Context) : OrderRepository{
    private val orderDao: OrderDao = AppDatabase.getInstance(context).orderDao()

    override suspend fun fetchOrders(token: String): List<Order> {
        return try {
            val ordersFromRemote = service.getOrders(token)
            withContext(Dispatchers.IO) {
                orderDao.insertOrders(ordersFromRemote)
            }

            ordersFromRemote
        } catch (e: Exception) {
            Log.d("RemoteFirstOrderRepository", e.message.toString())
            withContext(Dispatchers.IO) {
                orderDao.getOrders()
            }
        }
    }

    override suspend fun fetchOrderDetails(token: String, orderId: Int): List<OrderDetail> = service.getOrderDetails(token, orderId)

    override suspend fun addOrder(token: String, order: OrderRequest) = service.addOrder(token, order)

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5299/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: OrderService = retrofit.create(OrderService::class.java)
    }
}