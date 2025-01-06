package com.example.deliveryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.ui.screens.cart.CartViewModel
import com.example.deliveryapp.ui.viewModel.AddressViewModel
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.example.deliveryapp.ui.viewModel.UserViewModel

class HomeActivity : AppCompatActivity() {
    lateinit var orderViewModel: OrderViewModel
    lateinit var userViewModel: UserViewModel
    lateinit var cartViewModel: CartViewModel
    lateinit var addressViewModel: AddressViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]
        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        addressViewModel = ViewModelProvider(this)[AddressViewModel::class.java]

        setContent { DeliveryApp(supportFragmentManager) }
    }
}