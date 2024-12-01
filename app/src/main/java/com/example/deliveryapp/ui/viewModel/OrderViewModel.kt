package com.example.deliveryapp.ui.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryapp.data.model.Driver
import com.example.deliveryapp.data.model.DriverRating
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderRating
import com.example.deliveryapp.data.model.OrderStatus
import java.time.LocalDateTime

class OrderViewModel : ViewModel() {
    val _orders = MutableLiveData<List<Order>>()
    val _currentOrder = MutableLiveData<Order>()
    val _currentOrderDetails = MutableLiveData<List<OrderDetail>>()

    init {
        _orders.value = getOrders()
    }

    //TODO("Load orders from db")
    @SuppressLint("NewApi")
    private fun getOrders(): List<Order> {
        val sampleOrders = listOf(
            Order(
                id = 1,
                customerId = 1,
                restaurantId = "Cheese on Top",
                driver = Driver("20", "Antoine Karam", "2019-11-20", 9920),
                orderStatus = OrderStatus.DELIVERED,
                orderDate = "2024-11-14",
                deliveredDate = "2024-11-14",
                orderLocation = "123 Street",
                subtotal = 50.0,
                discountedPrice = 5.0,
                deliveryCharge = 2.0,
                paymentMethod = "Credit Card",
                orderRating = OrderRating.EXCELLENT,
                driverRating = DriverRating.LIKE
            ),
            Order(
                id = 0,
                customerId = 1,
                restaurantId = "Pizza Hut",
                driver = Driver("10", "Thor", "2023-11-20", 129),
                orderStatus = OrderStatus.DELIVERED,
                orderDate = "2024-11-14",
                deliveredDate = "2024-11-14",
                orderLocation = "123 Street",
                subtotal = 100.0,
                discountedPrice = 10.0,
                deliveryCharge = 1.0,
                paymentMethod = "Credit Card",
                orderRating = OrderRating.PENDING,
                driverRating = DriverRating.PENDING
            ),
            Order(
                id = 2,
                customerId = 1,
                restaurantId = "Boneless",
                orderStatus = OrderStatus.CONFIRMING,
                orderDate = "2024-11-13",
                deliveredDate = "2024-11-13",
                orderLocation = "456 Avenue",
                subtotal = 350.0,
                discountedPrice = 3.0,
                deliveryCharge = 1.0,
                paymentMethod = "Cash",
            ),
            Order(
                id = 3,
                customerId = 1,
                restaurantId = "Eddy's Boneless",
                orderStatus = OrderStatus.PREPARING_FETCHING_DRIVER,
                orderDate = "2024-11-13",
                deliveredDate = "2024-11-13",
                orderLocation = "456 Avenue",
                subtotal = 10.0,
                discountedPrice = 0.0,
                deliveryCharge = 1.0,
                paymentMethod = "Cash",
            ),
            Order(
                id = 4,
                customerId = 1,
                restaurantId = "Sandwich W Noss",
                driver = Driver("4", "Super Saiyan 4 Gogeta", "2022-11-20", 1230),
                orderStatus = OrderStatus.PREPARING_DRIVER_IN_STORE,
                orderDate = "2024-11-20",
                deliveredDate = "2024-11-21",
                orderLocation = "456 Avenue",
                subtotal = 7.0,
                discountedPrice = 0.0,
                deliveryCharge = 1.0,
                paymentMethod = "Cash",
            )
        )

        return sampleOrders
    }

    //TODO("Load clicked order details from db")
    fun getSelectedOrderDetails(orderId: Int) {
        _currentOrderDetails.value = listOf(
            OrderDetail(1, orderId, "Melt Your Brie Burger", 9.5, 1),
            OrderDetail(2, orderId, "Oh My Cheddar Burger", 20.0, 2)
        )
    }
}