package com.example.deliveryapp.ui.fragments.order.cards

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderDetailOrderCardDriverBinding
import com.example.deliveryapp.ui.fragments.order.OrderTrackFragment
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class OrderDetailOrderCardDriverFragment : Fragment() {

    private var _binding: FragmentOrderDetailOrderCardDriverBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailOrderCardDriverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        binding.trackOrderButton.setOnClickListener {
            val orderTrackFragment = OrderTrackFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction
                .replace(R.id.fragment_container, orderTrackFragment)
                .addToBackStack(null)
                .commit()
        }

        orderViewModel.currentOrder.value?.let { bind(it) }
    }

    private fun bind(order: Order) {
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.orderStatus)
        val driver = order.driver?.name
        val message = if (status == OrderStatus.DELIVERED) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.driverName.text = driver
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}