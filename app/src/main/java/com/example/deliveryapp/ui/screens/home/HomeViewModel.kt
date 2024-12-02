package com.example.deliveryapp.ui.screens.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.Restaurant
import com.example.deliveryapp.data.repository.RemoteRestaurantsRepository
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val restaurantsRepository: RemoteRestaurantsRepository = RemoteRestaurantsRepository()
    val restaurants: LiveData<List<Restaurant>> = _restaurants

    fun fetchRestaurants() {
        viewModelScope.launch {
            _restaurants.postValue(restaurantsRepository.fetchRestaurants())
        }
    }
}