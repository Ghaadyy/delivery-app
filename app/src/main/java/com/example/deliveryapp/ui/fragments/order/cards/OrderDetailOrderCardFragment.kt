package com.example.deliveryapp.ui.fragments.order.cards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderStatus
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

        orderViewModel.currentOrder.observe(viewLifecycleOwner) {
            bind(it)
        }
    }

    private fun bind(order: Order) {
        val status = OrderStatus.fromId(order.orderStatus)
        if(status != null){
            when (status) {
                OrderStatus.CONFIRMING, OrderStatus.PREPARING_FETCHING_DRIVER -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.OrderDetailCardFragmentContainer, OrderDetailOrderCardAssigningFragment())
                        .commit()
                }
                OrderStatus.PREPARING_DRIVER_GOING_TO_STORE,
                OrderStatus.PREPARING_DRIVER_IN_STORE,
                OrderStatus.ON_THE_WAY -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.OrderDetailCardFragmentContainer, OrderDetailOrderCardDriverFragment())
                        .commit()
                }
                OrderStatus.DELIVERED -> {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.OrderDetailCardFragmentContainer, OrderDetailOrderCardDeliveredFragment())
                        .commit()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
