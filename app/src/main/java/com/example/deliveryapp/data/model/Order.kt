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
    @ColumnInfo(name = "order_status_id") var orderStatus: Int,
    @ColumnInfo(name = "order_date") var orderDate: String,
    @ColumnInfo(name = "delivered_date") var deliveredDate: String,
    @Ignore val orderAddress: Address,
    @ColumnInfo(name = "subtotal") var subtotal: Double,
    @ColumnInfo(name = "discounted_price") var discountedPrice: Double,
    @ColumnInfo(name = "delivery_charge") var deliveryCharge: Double,
    @ColumnInfo(name = "payment_method") var paymentMethod: String,
    @ColumnInfo(name = "order_rating_id") var orderRating: Int,
    @ColumnInfo(name = "driver_rating_id") var driverRating: Int,
){
    constructor() : this(
        0, -1, "", null, 0, "", "", Address(1,  1,0.0, 0.0), 0.0, 0.0, 0.0, "", 0, 0
    )
}
