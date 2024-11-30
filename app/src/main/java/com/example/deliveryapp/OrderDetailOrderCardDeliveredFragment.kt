package com.example.deliveryapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderRating
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderDetailOrderCardDeliveredBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class OrderDetailOrderCardDeliveredFragment : Fragment() {
    private var _binding: FragmentOrderDetailOrderCardDeliveredBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailOrderCardDeliveredBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        orderViewModel._currentOrder.observe(viewLifecycleOwner) {
            bind(it)
        }

        binding.orderRatingButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetOrderRatingFragment.newInstance()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bind(order: Order){
        val restaurant = order.restaurantId
        val status = order.orderStatus
        val driver = order.driverId
        val message = if (status == OrderStatus.DELIVERED) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }
        val orderRating = order.orderRating

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.driverName.text = driver
        binding.orderRatingStars.rating = orderRating.ratingValue.toFloat()

        val viewsToHide: MutableList<View> = mutableListOf()
        if(orderRating != OrderRating.PENDING){
            viewsToHide.add(binding.orderRatingButton)
        }else{
            viewsToHide.add(binding.orderRatingText)
            viewsToHide.add(binding.orderRatingStars)
        }

        viewsToHide.forEach {it.visibility = View.INVISIBLE}
    }
}