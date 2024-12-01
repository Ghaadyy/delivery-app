package com.example.deliveryapp.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.model.Restaurant
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    fun fetchRestaurants() {
        val restaurants: List<Restaurant> = listOf(
            Restaurant("Burger Lovers", "Mansourieh"),
            Restaurant("Cheese on top", "Jal El Dib"),
            Restaurant("McDonalds", "Mansourieh"),
            Restaurant("Burger King", "Mansourieh"),
            Restaurant("Malak El Tawouk", "Mansourieh"),
            Restaurant("Sandwich W Noss", "Mansourieh"),
        )

        viewModelScope.launch {
            _restaurants.postValue(restaurants)
        }
    }
}