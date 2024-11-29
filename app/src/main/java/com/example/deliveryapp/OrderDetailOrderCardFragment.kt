package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.databinding.FragmentOrderDetailOrderCardBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class OrderDetailOrderCardFragment : Fragment() {

    private var _binding: FragmentOrderDetailOrderCardBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailOrderCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        orderViewModel._currentOrder.value?.let { bind(it) }

        binding.trackOrderButton.setOnClickListener {
            val orderTrackFragment = OrderTrackFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction
                .replace(R.id.fragment_container, orderTrackFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun bind(order: Order) {
        val restaurant = order.restaurantId
        val status = order.orderStatus
        val driver = order.driverId
        val message = if (status.id == 6) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.driverName.text = driver
        if (status.id == 1 || status.id == 2) {
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
        } else if (status.id != 6) {
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

    // Utility function to hide multiple views
    private fun hideViews(vararg views: View) {
        views.forEach { it.visibility = View.GONE }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
