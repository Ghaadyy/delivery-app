package com.example.deliveryapp.ui.fragments.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderListAdapter
import com.example.deliveryapp.ui.viewModel.UserViewModel

class OrderListFragment : Fragment() {
    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderAdapter: OrderListAdapter
    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView)
        orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        orderViewModel.getOrders()

        orderAdapter = OrderListAdapter(requireContext(), listOf(), orderViewModel)
        orderRecyclerView.adapter = orderAdapter

        orderViewModel.orders.observe(viewLifecycleOwner) { newOrders ->
            orderAdapter.updateOrders(newOrders)
        }
    }
}
