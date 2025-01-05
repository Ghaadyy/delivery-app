package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest

interface OrderRepository {
    suspend fun fetchOrders(): List<Order>
    suspend fun fetchOrderDetails(orderId: Int): List<OrderDetail>
    suspend fun addOrder(order: OrderRequest)
}