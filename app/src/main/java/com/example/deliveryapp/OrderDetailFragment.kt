package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderDetailBinding
import com.example.deliveryapp.ui.adapter.OrderDetailAdapter
import com.example.deliveryapp.ui.ViewModel.OrderViewModel

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderDetailAdapter: OrderDetailAdapter
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        orderDetailAdapter = OrderDetailAdapter(emptyList())
        binding.orderDetailRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderDetailRecyclerView.adapter = orderDetailAdapter

        orderViewModel._currentOrder.observe(viewLifecycleOwner) { order ->
            bindFirstCard(order)
            orderViewModel._currentOrderDetails.value?.let { bindSecondCard(it) }
            bindThirdCard(order)
        }

        binding.trackOrderButton.setOnClickListener {
            val orderTrackFragment = OrderTrackFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction
                .replace(R.id.fragment_container, orderTrackFragment)
                .addToBackStack(null)
                .commit()
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
        binding.orderDetailRecyclerView.adapter = orderDetailAdapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
