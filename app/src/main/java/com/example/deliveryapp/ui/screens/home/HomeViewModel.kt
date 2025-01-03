package com.example.deliveryapp.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.restaurant.Favorite
import com.example.deliveryapp.data.model.restaurant.Restaurant
import com.example.deliveryapp.data.repository.FavoritesRepository
import com.example.deliveryapp.data.repository.RemoteRestaurantsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val _restaurants = MutableLiveData<List<Restaurant>>()
    private val _favorites = MutableLiveData<List<Favorite>>()
    private val restaurantsRepository = RemoteRestaurantsRepository()
    private val favoritesRepository = FavoritesRepository(application)
    val restaurants: LiveData<List<Restaurant>> = _restaurants
    val favorites: LiveData<List<Favorite>> = _favorites

    fun fetchRestaurants() {
        viewModelScope.launch {
            _restaurants.postValue(restaurantsRepository.fetchRestaurants())
        }
    }

    fun fetchFavorites() {
        viewModelScope.launch {
            _favorites.postValue(favoritesRepository.getFavoriteRestaurants())
        }
    }

    fun likeRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            favoritesRepository.likeRestaurant(Favorite(restaurantId))
        }
    }

    fun dislikeRestaurant(restaurantId: Int) {
        viewModelScope.launch {
            favoritesRepository.dislikeRestaurant(Favorite(restaurantId))
        }
    }
}