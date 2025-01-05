package com.example.deliveryapp.ui.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.JsonPatch
import com.example.deliveryapp.data.model.TokenResponse
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.data.repository.RemoteUserRepository
import com.example.deliveryapp.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository = RemoteUserRepository()
    private val _user = MutableLiveData<User?>()
    private val _token = MutableLiveData<TokenResponse?>()
    private val _error = MutableLiveData<String?>()

    var user: LiveData<User?> = _user
    val token: LiveData<TokenResponse?> = _token
    val error: LiveData<String?> = _error

    fun setToken(token: String) {
        _token.value = TokenResponse(token)
    }

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

    fun fetchUserInfo(){
        viewModelScope.launch {
            val token = _token.value?.token
            if(token.isNullOrEmpty()){
                _error.value = "Token missing"
                return@launch
            }

            val result = userRepository.fetchUserInfo("Bearer $token")
            _user.value = result.getOrNull()
            Log.d("fetchUserInfo", _user.value.toString())

            if (result.isFailure){
                _error.value = result.getOrElse { e -> e.message }.toString()
                Log.d("fetchUserInfo", result.getOrElse { e -> e.message }.toString())
            }
        }
    }

    fun update(user: List<JsonPatch>){
        viewModelScope.launch {
            val token = _token.value?.token
            if(token.isNullOrEmpty()){
                _error.value = "Token missing"
                return@launch
            }

            val result = userRepository.update("Bearer $token", user)
            if(result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }
}