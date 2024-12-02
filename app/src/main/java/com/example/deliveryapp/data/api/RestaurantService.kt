package com.example.deliveryapp.data.api

import com.example.deliveryapp.data.model.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantService {
    @GET("restaurant")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurant/{restaurantId}")
    suspend fun getRestaurant(@Path("restaurantId") restaurantId: String): Restaurant

    @GET("restaurant/{restaurantId}/menu")
    suspend fun getMenu(@Path("restaurantId") restaurantId: String): Menu
}