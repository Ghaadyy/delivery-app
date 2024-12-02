package com.example.deliveryapp.ui.screens.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.model.Restaurant
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    val restaurant: LiveData<Restaurant> = _restaurant

    fun fetchRestaurant(restaurantId: String) {
        viewModelScope.launch {
            _restaurant.postValue(Restaurant("1","Burger Lovers", "Mansourieh"))
        }
    }
}