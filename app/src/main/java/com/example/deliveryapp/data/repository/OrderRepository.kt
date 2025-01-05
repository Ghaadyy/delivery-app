package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest

interface OrderRepository {
    suspend fun fetchOrders(token: String): List<Order>
    suspend fun fetchOrderDetails(token: String, orderId: Int): List<OrderDetail>
    suspend fun addOrder(token: String, order: OrderRequest)
}