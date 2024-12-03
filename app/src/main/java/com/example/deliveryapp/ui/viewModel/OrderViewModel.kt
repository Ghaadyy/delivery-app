package com.example.deliveryapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.repository.OrderRepository
import com.example.deliveryapp.data.repository.RemoteOrderRepository
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {
    private val orderRepository: OrderRepository = RemoteOrderRepository()

    val _orders = MutableLiveData<List<Order>>()
    val _currentOrder = MutableLiveData<Order>()
    val _currentOrderDetails = MutableLiveData<List<OrderDetail>>()


    fun getOrders() {
        viewModelScope.launch {
            _orders.value = orderRepository.fetchOrders()
        }
    }

    fun getSelectedOrderDetails(orderId: Int) {
        viewModelScope.launch {
            _currentOrderDetails.value = orderRepository.fetchOrderDetails(orderId)
        }
    }
}