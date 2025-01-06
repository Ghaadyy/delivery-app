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
import com.example.deliveryapp.token.TokenManager
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {
    private val userRepository: UserRepository = RemoteUserRepository(application)
    private val _user = MutableLiveData<User?>()
    private val _error = MutableLiveData<String?>()
    private val _loginSuccess = MutableLiveData(false)

    var user: LiveData<User?> = _user
    val error: LiveData<String?> = _error
    val loginSuccess: LiveData<Boolean> = _loginSuccess

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.login(User(null.toString(),
                null.toString(), email, null, null, password))
            if (result.isSuccess){
                TokenManager.saveToken(result.getOrNull()!!.token)
                _loginSuccess.value = true
            }else{
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun signup(firstName: String, lastName: String, email: String, password: String) {
        viewModelScope.launch {
            val result = userRepository.signup(User(firstName,
                lastName, email, null, null, password))
            if (result.isSuccess){
                TokenManager.saveToken(result.getOrNull()!!.token)
                _loginSuccess.value = true

            }else{
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun fetchUserInfo(){
        viewModelScope.launch {
            val result = userRepository.fetchUserInfo()
            if(result.isSuccess){
                _user.value = result.getOrNull()
            }else {
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun update(user: List<JsonPatch>){
        viewModelScope.launch {
            val result = userRepository.update(user)
            if(result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun clearError() {
        _error.value = null
    }

}