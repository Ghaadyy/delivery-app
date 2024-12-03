package com.example.deliveryapp.data.api

import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants/{restaurantId}")
    suspend fun getRestaurant(@Path("restaurantId") restaurantId: String): Restaurant

    @GET("menu/{restaurantId}")
    suspend fun getMenu(@Path("restaurantId") restaurantId: String): Menu
}