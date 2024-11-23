package com.example.deliveryapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
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
import com.example.deliveryapp.databinding.ActivityOrderDetailBinding
import com.example.deliveryapp.ui.ViewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderAdapter
import com.example.deliveryapp.ui.adapter.OrderDetailAdapter

class OrderDetailActivity : AppCompatActivity() {
    private lateinit var orderDetailRecyclerView: RecyclerView
    private lateinit var orderDetailAdapter: OrderDetailAdapter
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var binding: ActivityOrderDetailBinding

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        orderDetailRecyclerView = findViewById(R.id.orderDetailRecyclerView)
        orderDetailRecyclerView.layoutManager = LinearLayoutManager(this)

        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]

        val order = intent.getSerializableExtra("order", Order::class.java)
        order?.let {
            val orderDetails = orderViewModel.getOrderDetails(order.id)
            bindFirstCard(order)
            bindSecondCard(orderDetails)
            bindThirdCard(order)
        }

        binding.trackOrderButton.setOnClickListener {
            val intent = Intent(this, TrackOrderActivity::class.java)
            intent.putExtra("order", order)
            startActivity(intent)
            finish()
        }
    }

    private fun bindFirstCard(order: Order){
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.status)
        val driver = order.driverId
        val message = if(status?.id == 6) {
            "${status.label} on ${order.deliveredDate}"
        }else{
            "Placed at ${order.orderDate}"
        }

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.driverName.text = driver
        if(status?.id == 1 || status?.id == 2){
            binding.trackOrderButton.visibility = View.VISIBLE
            hideViews(
                binding.driverLogo,
                binding.driverName,
                binding.dividerFirstCardSection2,
                binding.reorderButton,
                binding.dividerFirstCardSection3,
                binding.orderRatingText,
                binding.orderRatingStars,
                binding.dividerFirstCardSection4,
                binding.driverRatingText,
                binding.driverRatingLogo
            )
        }else if(status?.id != 6){
            binding.trackOrderButton.visibility = View.VISIBLE
            hideViews(
                binding.reorderButton,
                binding.dividerFirstCardSection3,
                binding.orderRatingText,
                binding.orderRatingStars,
                binding.dividerFirstCardSection4,
                binding.driverRatingText,
                binding.driverRatingLogo
            )
        }
    }

    private fun bindSecondCard(orderDetails: List<OrderDetail>){
        orderDetailAdapter = OrderDetailAdapter(orderDetails)
        orderDetailRecyclerView.adapter = orderDetailAdapter
    }

    private fun bindThirdCard(order: Order){
        val subtotal = "USD ${order.subtotal}"
        val deliveryCharge = "USD ${order.deliveryCharge}"
        //TODO("Add discount")
        val total = "USD ${order.subtotal + order.deliveryCharge}"

        binding.subTotal.text = subtotal
        binding.deliveryCharge.text = deliveryCharge
        binding.total.text = total
    }

    /**
     * Utility function to hide multiple views
     * */
    private fun hideViews(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }
}