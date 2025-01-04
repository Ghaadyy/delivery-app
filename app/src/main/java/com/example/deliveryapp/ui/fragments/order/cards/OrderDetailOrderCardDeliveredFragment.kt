package com.example.deliveryapp.ui.fragments.order.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.DriverRating
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderRating
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderDetailOrderCardDeliveredBinding
import com.example.deliveryapp.ui.fragments.bottomsheet.BottomSheetDriverRatingFragment
import com.example.deliveryapp.ui.fragments.bottomsheet.BottomSheetOrderRatingFragment
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

        orderViewModel.currentOrder.observe(viewLifecycleOwner) {
            bind(it)
        }

        binding.orderRatingButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetOrderRatingFragment.newInstance()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.driverRatingButton.setOnClickListener {
            val bottomSheetFragment = BottomSheetDriverRatingFragment.newInstance()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }

        binding.reorderButton.setOnClickListener {
            //TODO("Redirect the user to the checkout with the card filled with the same order")
        }
    }

    private fun bind(order: Order){
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.orderStatus)
        val driver = order.driver?.name
        val message = if (status == OrderStatus.DELIVERED) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }
        val orderRating = OrderRating.fromId(order.orderRating)
        val driverRating = DriverRating.fromId(order.driverRating)

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.driverName.text = driver
        binding.orderRatingStars.rating = orderRating?.ratingValue?.toFloat()!!

        val viewsToHide: MutableList<View> = mutableListOf()
        if(orderRating != OrderRating.PENDING){
            viewsToHide.add(binding.orderRatingButton)
        }else{
            viewsToHide.add(binding.orderRatingText)
            viewsToHide.add(binding.orderRatingStars)
        }

        if(driverRating != DriverRating.PENDING){
            viewsToHide.add(binding.driverRatingButton)
            if(driverRating == DriverRating.LIKE){
                viewsToHide.add(binding.dislikeButton)
            }else if(driverRating == DriverRating.DISLIKE){
                viewsToHide.add(binding.likeButton)
            }
        }else{
            viewsToHide.add(binding.driverRatingText)
            viewsToHide.add(binding.likeButton)
            viewsToHide.add(binding.dislikeButton)
        }

        viewsToHide.forEach {it.visibility = View.INVISIBLE}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}