package com.example.deliveryapp.ui.screens.cart

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.MealRequest
import com.example.deliveryapp.data.repository.CartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val cartRepository = CartRepository(application)

    private val _cart: MutableLiveData<List<MealRequest>> = MutableLiveData()
    private val _selectedRestaurantId: MutableLiveData<Int?> = MutableLiveData()
    val cart: LiveData<List<MealRequest>> = _cart
    val selectedRestaurantId: LiveData<Int?> = _selectedRestaurantId

    suspend fun getCart() = withContext(Dispatchers.IO) {
        val c = cartRepository.getCart()
        viewModelScope.launch {
            _cart.value = c
        }
    }

    suspend fun addToCart(restaurantId: Int, mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        if(_selectedRestaurantId.value != null && restaurantId != _selectedRestaurantId.value) return@withContext

        viewModelScope.launch { _selectedRestaurantId.value = restaurantId }
        cartRepository.addToCart(mealRequest)
        getCart()
    }

    suspend fun removeFromCart(mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        cartRepository.removeFromCart(mealRequest)
        getCart()
        if(cart.value!!.isEmpty()) {
            viewModelScope.launch {  _selectedRestaurantId.value = null }
        }
    }

    suspend fun updateItem(mealRequest: MealRequest) = withContext(Dispatchers.IO) {
        cartRepository.updateItem(mealRequest)
        getCart()
    }

    suspend fun clearCart() = withContext(Dispatchers.IO) {
        cartRepository.clearCart()
        cartRepository.getCart()
        viewModelScope.launch {
            _cart.value = emptyList()
            _selectedRestaurantId.value = null
        }
    }
}