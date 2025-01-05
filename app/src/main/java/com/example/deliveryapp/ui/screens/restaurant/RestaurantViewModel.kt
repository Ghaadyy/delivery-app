package com.example.deliveryapp.ui.screens.restaurant

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.model.restaurant.Review
import com.example.deliveryapp.data.repository.RemoteRestaurantsRepository
import com.example.deliveryapp.data.repository.ReviewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {
    private val _restaurant = MutableLiveData<Restaurant>()
    private val _menu = MutableLiveData<Menu>()
    private val _reviews = MutableLiveData<List<Review>>()
    private val restaurantsRepository = RemoteRestaurantsRepository()
    private val reviewsRestaurant = ReviewsRepository(application)
    private val _errorMessage = MutableLiveData<String?>()
    val restaurant: LiveData<Restaurant> = _restaurant
    val menu: LiveData<Menu> = _menu
    val reviews: LiveData<List<Review>> = _reviews
    val errorMessage: LiveData<String?> = _errorMessage

    fun clearErrors() {
        _errorMessage.postValue(null)
    }

    fun fetchRestaurant(token: String, restaurantId: Int) {
        viewModelScope.launch {
            val res = restaurantsRepository.fetchRestaurant("Bearer $token", restaurantId)
            if(res.isSuccess)
                _restaurant.postValue(res.getOrNull()!!)
            else
                _errorMessage.postValue("Failed to fetch restaurant: ${res.exceptionOrNull()?.message}")
        }
    }

    fun fetchMenu(token: String, restaurantId: Int) {
        viewModelScope.launch {
            val res = restaurantsRepository.fetchMenu("Bearer $token", restaurantId)
            if(res.isSuccess)
                _menu.postValue(res.getOrNull()!!)
            else
                _errorMessage.postValue("Failed to fetch menu: ${res.exceptionOrNull()?.message}")
        }
    }

    fun getRestaurantReviews(token: String, restaurantId: Int) {
        viewModelScope.launch {
            val res = restaurantsRepository.fetchReviews("Bearer $token", restaurantId)
            if(res.isSuccess)
                _reviews.postValue(res.getOrNull()!!)
            else
                _errorMessage.postValue("Failed to fetch reviews: ${res.exceptionOrNull()?.message}")
        }
    }

    suspend fun addReview(token: String, review: Review) {
        viewModelScope.launch {
            val res = restaurantsRepository.addReview("Bearer $token", review)
            if(!res.isSuccess)
                _errorMessage.postValue("Failed to fetch restaurant: ${res.exceptionOrNull()?.message}")
        }
    }
}