package com.example.deliveryapp.data.repository

import android.content.Context
import android.util.Log
import com.example.deliveryapp.data.local.AppDatabase
import com.example.deliveryapp.data.local.OrderDao
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest
import com.example.deliveryapp.data.service.OrderService
import com.example.deliveryapp.token.TokenInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
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

    override suspend fun fetchOrders(): Result<List<Order>> =
        callOrderService(
            updateCache = { orders ->
                withContext(Dispatchers.IO) {
                    orderDao.insertOrders(orders)
                }
            }
        ) { service.getOrders() }
            .onFailure {
                return Result.success(withContext(Dispatchers.IO) {
                    orderDao.getOrders()
                })
            }

    override suspend fun fetchOrderDetails(orderId: Int): Result<List<OrderDetail>> = callOrderService { service.getOrderDetails(orderId) }

    override suspend fun addOrder(order: OrderRequest): Result<Unit> = callOrderService { service.addOrder(order) }

    companion object {
        private fun getOkHttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(context))
                .build()
        }

        private fun getRetrofit(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5299/")
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private val service: OrderService = getRetrofit(context).create(OrderService::class.java)
}