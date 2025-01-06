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
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteFirstOrderRepository(context: Context) : OrderRepository{
    private val orderDao: OrderDao = AppDatabase.getInstance(context).orderDao()

    private suspend fun <T> callOrderService(updateCache: suspend (T) -> Unit = {}, call: suspend () -> Response<T>): Result<T> {
        return kotlin.runCatching { call() }
            .fold(
                onSuccess = { res ->
                    if (res.isSuccessful) {
                        val orders = res.body()!!
                        updateCache(orders)
                        Result.success(orders)
                    }
                    else Result.failure(Exception(res.message()))
                },
                onFailure = { e -> Result.failure(e) }
            )
    }

    override suspend fun fetchOrders(token: String): Result<List<Order>> =
        callOrderService(
            updateCache = { orders ->
                withContext(Dispatchers.IO) {
                    orderDao.insertOrders(orders)
                }
            }
        ) { service.getOrders(token) }
            .onFailure {
                return Result.success(withContext(Dispatchers.IO) {
                    orderDao.getOrders()
                })
            }

    override suspend fun fetchOrderDetails(token: String, orderId: Int): Result<List<OrderDetail>> = callOrderService { service.getOrderDetails(token, orderId) }

    override suspend fun addOrder(token: String, order: OrderRequest): Result<Unit> = callOrderService { service.addOrder(token, order) }

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5299/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: OrderService = retrofit.create(OrderService::class.java)
    }
}