package com.example.deliveryapp.data.service

import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface OrderService {

    @GET("orders")
    suspend fun getOrders(): List<Order>

    @GET("orders/{orderId}/details")
    suspend fun getOrderDetails(@Path("orderId") orderId: Int): List<OrderDetail>
}