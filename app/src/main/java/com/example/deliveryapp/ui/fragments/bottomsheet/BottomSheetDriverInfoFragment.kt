package com.example.deliveryapp.ui.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.Driver
import com.example.deliveryapp.databinding.FragmentBottomSheetDriverInfoBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDriverInfoFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDriverInfoBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDriverInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        val currentOrder = orderViewModel.currentOrder.value
        if(currentOrder?.driver != null) bind(currentOrder.driver)
    }

    private fun bind(driver: Driver){
        val driverName = driver.name
        val ordersTaken = driver.orders.toString()
        val startDate = driver.startDate

        binding.driverName.text = driverName
        binding.driverOrders.text = ordersTaken
        binding.driverStartDate.text = startDate
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): BottomSheetDriverInfoFragment {
            return BottomSheetDriverInfoFragment()
        }
    }
}