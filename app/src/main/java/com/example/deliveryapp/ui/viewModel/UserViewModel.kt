package com.example.deliveryapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.data.repository.RemoteUserRepository
import com.example.deliveryapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository = RemoteUserRepository()
    private val _user = MutableLiveData<User>()
    private val _token = MutableLiveData<TokenResponse?>()
    private val _error = MutableLiveData<String?>()

    var user: LiveData<User> = _user
    val token: LiveData<TokenResponse?> = _token
    val error: LiveData<String?> = _error

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(User(null.toString(),
                null.toString(), email, null, null, password))
            _token.value = result.getOrNull()
            if (result.isFailure){
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun signup(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.signup(User(firstName,
                lastName, email, null, null, password))
            _token.value = result.getOrNull()
            if (result.isFailure){
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}