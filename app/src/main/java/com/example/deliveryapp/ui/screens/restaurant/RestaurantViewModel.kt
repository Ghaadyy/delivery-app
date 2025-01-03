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
    val restaurant: LiveData<Restaurant> = _restaurant
    val menu: LiveData<Menu> = _menu
    val reviews: LiveData<List<Review>> = _reviews

    fun fetchRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            _restaurant.postValue(restaurantsRepository.fetchRestaurant(restaurantId))
        }
    }

    fun fetchMenu(restaurantId: Int) {
        viewModelScope.launch {
            _menu.postValue(restaurantsRepository.fetchMenu(restaurantId))
        }
    }

    fun getRestaurantReviews(restaurantId: Int) {
        viewModelScope.launch {
            _reviews.postValue(restaurantsRepository.fetchReviews(restaurantId))
        }
    }

    suspend fun addReview(review: Review) {
        viewModelScope.launch {
            restaurantsRepository.addReview(review)
        }
    }
}