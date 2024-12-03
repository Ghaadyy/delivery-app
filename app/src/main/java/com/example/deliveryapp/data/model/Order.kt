package com.example.deliveryapp.data.model

data class Order(
    val id: Int,
    val customerId: Int,
    val restaurantId: String,
    val driver: Driver? = null,
    var orderStatusId: Int,
    val orderDate: String,
    val deliveredDate: String,
    val orderLocation: Location,
    val subtotal: Double,
    val discountedPrice: Double,
    val deliveryCharge: Double,
    val paymentMethod: String,
    var orderRatingId: Int,
    var driverRatingId: Int,
){
    val orderStatus: OrderStatus?
        get() = OrderStatus.fromId(orderStatusId)

    val orderRating: OrderRating?
        get() = OrderRating.fromId(orderRatingId)

    val driverRating: DriverRating?
        get() = DriverRating.fromId(driverRatingId)
}
