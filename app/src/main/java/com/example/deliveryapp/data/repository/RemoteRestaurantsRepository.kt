package com.example.deliveryapp.data.repository

import android.content.Context
import com.example.deliveryapp.data.service.RestaurantService
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Review
import com.example.deliveryapp.token.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRestaurantsRepository(context: Context) : RestaurantsRepository {
    override suspend fun fetchRestaurants(): Result<List<Restaurant>> {
        return try {
            val res = service.getRestaurants()

            if(res.isSuccessful)
                Result.success(res.body()!!)
            else
                Result.failure(Exception(res.message()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchRestaurant(id: Int): Result<Restaurant> {
        return try {
            val res = service.getRestaurant(id)

            if(res.isSuccessful)
                Result.success(res.body()!!)
            else
                Result.failure(Exception(res.message()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchMenu(restaurantId: Int): Result<Menu> {
        return try {
            val res = service.getMenu(restaurantId)

            if(res.isSuccessful)
                Result.success(res.body()!!)
            else
                Result.failure(Exception(res.message()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchReviews(restaurantId: Int): Result<List<Review>> {
        return try {
            val res = service.getReviews(restaurantId)

            if(res.isSuccessful)
                Result.success(res.body()!!)
            else
                Result.failure(Exception(res.message()))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun addReview(review: Review): Result<Unit> {
        try {
            val res = service.addReview(review.restaurantId, review)

            return if(!res.isSuccessful)
                Result.failure(Exception(res.message()))
            else
                Result.success(Unit)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    companion object {
        private fun getOkHttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(context))
                .build()
        }

        private fun getRetrofit(context: Context): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://10.0.2.2:5299/")
                .client(getOkHttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private val service: RestaurantService = getRetrofit(context).create(RestaurantService::class.java)
}