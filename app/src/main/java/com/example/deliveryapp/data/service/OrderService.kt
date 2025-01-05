package com.example.deliveryapp.data.service

import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface OrderService {

    @GET("orders")
    suspend fun getOrders(@Header("Authorization") token: String): List<Order>

    @GET("orders/{orderId}/details")
    suspend fun getOrderDetails(@Header("Authorization") token: String, @Path("orderId") orderId: Int): List<OrderDetail>

    @POST("orders")
    suspend fun addOrder(@Header("Authorization") token: String, @Body order: OrderRequest)
}