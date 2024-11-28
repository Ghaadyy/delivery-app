package com.example.deliveryapp

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderTrackBinding
import com.example.deliveryapp.ui.ViewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderTrackAdapter

class OrderTrackFragment : Fragment(R.layout.fragment_order_track) {
    private lateinit var orderTrackAdapter: OrderTrackAdapter
    private lateinit var orderViewModel: OrderViewModel
    private var _binding: FragmentOrderTrackBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrderTrackBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.orderSummaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        orderViewModel._currentOrder.observe(viewLifecycleOwner) { order ->
            bindCard(order)
            orderViewModel._currentOrderDetails.value?.let { bindOrderSummary(it) }
        }
    }

    private fun bindCard(order: Order) {
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.status)
        val message = if (status?.id == 6) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }
        val trackOrderTopMessage = status?.label
        val totalPrice = "USD ${order.subtotal + order.deliveryCharge - order.discountedPrice}"

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.trackOrderStatus.text = trackOrderTopMessage
        binding.total.text = totalPrice
    }

    private fun bindOrderSummary(orderDetails: List<OrderDetail>) {
        orderTrackAdapter = OrderTrackAdapter(orderDetails)
        binding.orderSummaryRecyclerView.adapter = orderTrackAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
