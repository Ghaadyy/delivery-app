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
            Restaurant("1","Burger Lovers", "Mansourieh"),
            Restaurant("2","Cheese on top", "Jal El Dib"),
            Restaurant("3","McDonalds", "Mansourieh"),
            Restaurant("4","Burger King", "Mansourieh"),
            Restaurant("5","Malak El Tawouk", "Mansourieh"),
            Restaurant("6","Sandwich W Noss", "Mansourieh"),
        )

        viewModelScope.launch {
            _restaurants.postValue(restaurants)
        }
    }
}