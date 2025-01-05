package com.example.deliveryapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.User
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.example.deliveryapp.ui.viewModel.UserViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel
    lateinit var userViewModel: UserViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        val token = intent.getStringExtra("token")

        userViewModel.setToken(token!!)

        setContent { DeliveryApp(supportFragmentManager) }
    }
}