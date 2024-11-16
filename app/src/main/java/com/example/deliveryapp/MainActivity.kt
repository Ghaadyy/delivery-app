package com.example.deliveryapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.ui.adapter.OrderAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var orders: List<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        orderRecyclerView = findViewById(R.id.orderRecyclerView)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)

        orders = listOf(
            Order(1, 1, "Cheese on top", 1, "PENDING", "2024-11-14", "123 Street", 50.0, 5.0, 2.0, "Credit Card", 4, true),
            Order(2, 1, "Boneless", 2, "DELIVERED", "2024-11-13", "456 Avenue", 350.0,3.0, 1.0, "Cash", 5, false)
        )

        orderAdapter = OrderAdapter(orders)
        orderRecyclerView.adapter = orderAdapter
    }
}