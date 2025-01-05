package com.example.deliveryapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRequest
import com.example.deliveryapp.data.repository.OrderRepository
import com.example.deliveryapp.data.repository.RemoteFirstOrderRepository
import com.example.deliveryapp.data.repository.RemoteRestaurantsRepository
import com.example.deliveryapp.data.repository.RestaurantsRepository
import kotlinx.coroutines.launch

class OrderViewModel(application: Application) : AndroidViewModel(application) {
    private val orderRepository: OrderRepository = RemoteFirstOrderRepository(application)
    private val _orders = MutableLiveData<List<Order>>()
    private val _currentOrder = MutableLiveData<Order>()
    private val _currentOrderDetails = MutableLiveData<List<OrderDetail>>()

    var orders: LiveData<List<Order>> = _orders
    val currentOrder: LiveData<Order> = _currentOrder
    val currentOrderDetails: LiveData<List<OrderDetail>> = _currentOrderDetails

    fun setOrders(orders: List<Order>) { _orders.value = orders }
    fun setCurrentOrder(order: Order) { _currentOrder.value = order }
    fun setCurrentOrderDetails(orderDetails: List<OrderDetail>) { _currentOrderDetails.value = orderDetails }

    fun getOrders(token: String) {
        viewModelScope.launch {
            _orders.value = orderRepository.fetchOrders("Bearer $token")
        }
    }

    fun addOrder(token: String, order: OrderRequest) {
        viewModelScope.launch {
            orderRepository.addOrder("Bearer $token", order)
        }
    }

    fun getSelectedOrderDetails(token: String, orderId: Int) {
        viewModelScope.launch {
            _currentOrderDetails.value = orderRepository.fetchOrderDetails("Bearer $token", orderId)
        }
    }
}