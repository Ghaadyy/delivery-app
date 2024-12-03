package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.service.OrderService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteOrderRepository : OrderRepository{
    override suspend fun fetchOrders(): List<Order> = service.getOrders()

    override suspend fun fetchOrderDetails(orderId: Int): List<OrderDetail> = service.getOrderDetails(orderId)

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:5299/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: OrderService = retrofit.create(OrderService::class.java)
    }
}