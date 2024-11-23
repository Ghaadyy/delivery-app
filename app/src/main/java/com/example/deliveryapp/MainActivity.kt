package com.example.deliveryapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.ui.ViewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        orderAdapter = OrderAdapter(this, emptyList())

        orderRecyclerView = findViewById(R.id.orderRecyclerView)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.adapter = orderAdapter

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        orderViewModel.orders.observe(this) {
            orderViewModel.orders.value?.let { it1 -> orderAdapter.updateOrders(it1) }
        }
    }
}