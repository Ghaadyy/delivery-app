package com.example.deliveryapp.ui.screens.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.model.menu.Menu
import com.example.deliveryapp.data.repository.RemoteRestaurantsRepository
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val _restaurant = MutableLiveData<Restaurant>()
    private val _menu = MutableLiveData<Menu>()
    private val restaurantsRepository: RemoteRestaurantsRepository = RemoteRestaurantsRepository()
    val restaurant: LiveData<Restaurant> = _restaurant
    val menu: LiveData<Menu> = _menu

    fun fetchRestaurant(restaurantId: String) {
        viewModelScope.launch {
            _restaurant.postValue(restaurantsRepository.fetchRestaurant(restaurantId))
        }
    }

    fun fetchMenu(restaurantId: String) {
        viewModelScope.launch {
            _menu.postValue(restaurantsRepository.fetchMenu(restaurantId))
        }
    }
}