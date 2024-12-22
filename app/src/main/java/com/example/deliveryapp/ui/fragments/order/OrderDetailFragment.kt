package com.example.deliveryapp.ui.fragments.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.ui.fragments.order.cards.OrderDetailOrderCardFragment
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.databinding.FragmentOrderDetailBinding
import com.example.deliveryapp.ui.adapter.OrderDetailAdapter
import com.example.deliveryapp.ui.viewModel.OrderViewModel

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

        orderViewModel.currentOrder.observe(viewLifecycleOwner) { order ->
            bindFirstCard()
            bindThirdCard(order)
        }

        orderViewModel.currentOrderDetails.observe(viewLifecycleOwner) {
            bindSecondCard(it)
        }
    }

    private fun bindFirstCard() {
        childFragmentManager.beginTransaction()
            .replace(R.id.orderCardFragment, OrderDetailOrderCardFragment())
            .commit()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
