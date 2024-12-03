package com.example.deliveryapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "orders",)
data class Order(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "customer_id") var customerId: Int,
    @ColumnInfo(name = "restaurant_id") var restaurantId: String,
    @Ignore val driver: Driver? = null,
    @ColumnInfo(name = "order_status_id") var orderStatusId: Int,
    @ColumnInfo(name = "order_date") var orderDate: String,
    @ColumnInfo(name = "delivered_date") var deliveredDate: String,
    @Ignore val orderLocation: Location,
    @ColumnInfo(name = "subtotal") var subtotal: Double,
    @ColumnInfo(name = "discounted_price") var discountedPrice: Double,
    @ColumnInfo(name = "delivery_charge") var deliveryCharge: Double,
    @ColumnInfo(name = "payment_method") var paymentMethod: String,
    @ColumnInfo(name = "order_rating_id") var orderRatingId: Int,
    @ColumnInfo(name = "driver_rating_id") var driverRatingId: Int,
){
    constructor() : this(
        0, -1, "", null, 0, "", "", Location(0.0, 0.0), 0.0, 0.0, 0.0, "", 0, 0
    )

    val orderStatus: OrderStatus?
        get() = OrderStatus.fromId(orderStatusId)

    val orderRating: OrderRating?
        get() = OrderRating.fromId(orderRatingId)

    val driverRating: DriverRating?
        get() = DriverRating.fromId(driverRatingId)
}
