package com.example.deliveryapp.data.api

import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Review
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RestaurantService {
    @GET("restaurants")
    suspend fun getRestaurants(): List<Restaurant>

    @GET("restaurants/{restaurantId}")
    suspend fun getRestaurant(@Path("restaurantId") restaurantId: Int): Restaurant

    @GET("restaurants/{restaurantId}/menu")
    suspend fun getMenu(@Path("restaurantId") restaurantId: Int): Menu

    @GET("restaurants/{restaurantId}/reviews")
    suspend fun getReviews(@Path("restaurantId") restaurantId: Int): List<Review>

    @POST("restaurants/{restaurantId}/reviews")
    suspend fun addReview(@Path("restaurantId") restaurantId: Int, @Body review: Review)
}