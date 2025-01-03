package com.example.deliveryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var orderViewModel: OrderViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        orderViewModel.getOrders()

        setContent { DeliveryApp(supportFragmentManager) }
    }
}