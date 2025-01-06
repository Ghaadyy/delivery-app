package com.example.deliveryapp.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.data.model.Address
import com.example.deliveryapp.data.repository.RemoteUserRepository
import kotlinx.coroutines.launch

class AddressViewModel(application: Application) : AndroidViewModel(application) {
    private val userRepository = RemoteUserRepository(application)

    private val _addresses: MutableLiveData<List<Address>> = MutableLiveData()
    private val _error: MutableLiveData<String?> = MutableLiveData()
    private val _selectedAddress: MutableLiveData<Address?> = MutableLiveData()

    val addresses: LiveData<List<Address>> = _addresses
    val selectedAddress: LiveData<Address?> = _selectedAddress
    val error: LiveData<String?> = _error

    fun getAddresses() {
        viewModelScope.launch {
            val result = userRepository.getAddresses()
            if(result.isSuccess) {
                _addresses.value = result.getOrNull()!!
            } else {
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun addAddress(address: Address) {
        viewModelScope.launch {
            val result = userRepository.addAddress(address)
            if(result.isFailure) {
                _error.value = result.getOrElse { e -> e.message }.toString()
            }
        }
    }

    fun selectAddress(address: Address) {
        _selectedAddress.value = address
    }

    fun clearError() {
        _error.value = null
    }
}