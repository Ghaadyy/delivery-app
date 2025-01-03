package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.api.RestaurantService
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Review
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRestaurantsRepository : RestaurantsRepository {
    override suspend fun fetchRestaurants(): List<Restaurant> = service.getRestaurants()

    override suspend fun fetchRestaurant(id: Int): Restaurant = service.getRestaurant(id)

    override suspend fun fetchMenu(restaurantId: Int): Menu = service.getMenu(restaurantId)

    override suspend fun fetchReviews(restaurantId: Int): List<Review> = service.getReviews(restaurantId)

    override suspend fun addReview(review: Review) = service.addReview(review.restaurantId, review)

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://674deb94635bad45618d37ef.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: RestaurantService = retrofit.create(RestaurantService::class.java)
    }
}