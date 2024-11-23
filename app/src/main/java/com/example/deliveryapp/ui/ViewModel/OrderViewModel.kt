package com.example.deliveryapp.ui.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail

class OrderViewModel : ViewModel() {
    val orders = MutableLiveData<List<Order>>()

    init {
        orders.value = getOrders()
    }

    //TODO("Load orders from db")
    private fun getOrders(): List<Order> {
        val sampleOrders = listOf(
            Order(
                id = 1,
                customerId = 1,
                restaurantId = "Cheese on Top",
                driverId = "Antoine Karam",
                status = 6,
                orderDate = "2024-11-14",
                deliveredDate = "2024-11-14",
                orderLocation = "123 Street",
                subtotal = 50.0,
                discountedPrice = 5.0,
                deliveryCharge = 2.0,
                paymentMethod = "Credit Card",
                orderRating = 4,
                driverRating = 1
            ),
            Order(
                id = 2,
                customerId = 1,
                restaurantId = "Boneless",
                status = 1,
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
                status = 2,
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
                driverId = "Super Saiyan 4 Gogeta",
                status = 4,
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

    //TODO("Load orders details from db")
    fun getOrderDetails(orderId: Int): List<OrderDetail> {
        val sampleOrderDetail = listOf(
            OrderDetail(1, orderId, "Melt Your Brie Burger", 9.5, 1),
            OrderDetail(2, orderId, "Oh My Cheddar Burger", 20.0, 2)
        )

        return sampleOrderDetail
    }
}