package com.example.deliveryapp

import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.ActivityTrackOrderBinding
import com.example.deliveryapp.ui.ViewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderAdapter
import com.example.deliveryapp.ui.adapter.OrderDetailAdapter
import com.example.deliveryapp.ui.adapter.OrderSummaryAdapter

class TrackOrderActivity : AppCompatActivity() {
    private lateinit var trackOrderRecyclerView: RecyclerView
    private lateinit var trackOrderAdapter: OrderSummaryAdapter
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: ActivityTrackOrderBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        trackOrderRecyclerView = findViewById(R.id.orderSummaryRecyclerView)
        trackOrderRecyclerView.layoutManager = LinearLayoutManager(this)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val order = intent.getSerializableExtra("order", Order::class.java)
        order?.let {
            val orderDetails = orderViewModel.getOrderDetails(order.id)
            bindCard(order)
            bindOrderSummary(orderDetails)
        }
    }

    private fun bindCard(order: Order){
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.status)
        val message = if(status?.id == 6) {
            "${status.label} on ${order.deliveredDate}"
        }else{
            "Placed at ${order.orderDate}"
        }
        val trackOrderTopMessage = status?.label
        val totalPrice = "USD ${order.subtotal + order.deliveryCharge - order.discountedPrice}"

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.trackOrderStatus.text = trackOrderTopMessage
        binding.total.text = totalPrice
    }

    private fun bindOrderSummary(orderDetails: List<OrderDetail>){
        trackOrderAdapter = OrderSummaryAdapter(orderDetails)
        trackOrderRecyclerView.adapter = trackOrderAdapter
    }
}