package com.example.deliveryapp.data.repository

import com.example.deliveryapp.data.api.RestaurantService
import com.example.deliveryapp.data.model.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRestaurantsRepository : RestaurantsRepository {
    override suspend fun fetchRestaurants(): List<Restaurant> = service.getRestaurants()

    override suspend fun fetchRestaurant(id: String): Restaurant = service.getRestaurant(id)

    override suspend fun fetchMenu(restaurantId: String): Menu = service.getMenu(restaurantId)

    companion object {
        private val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://674deb94635bad45618d37ef.mockapi.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        private val service: RestaurantService = retrofit.create(RestaurantService::class.java)
    }
}