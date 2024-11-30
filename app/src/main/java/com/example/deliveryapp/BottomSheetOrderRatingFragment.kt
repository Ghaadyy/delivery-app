package com.example.deliveryapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.OrderRating
import com.example.deliveryapp.databinding.FragmentBottomSheetOrderRatingBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetOrderRatingFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetOrderRatingBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetOrderRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        binding.submitRatingButton.setOnClickListener{
            val currentOrder = orderViewModel._currentOrder
            if(currentOrder.value?.orderRating == OrderRating.PENDING){
                currentOrder.value?.let { order ->
                    order.orderRating = OrderRating.entries.find { it.ratingValue == binding.ratingBar.rating.toInt() }!!
                    orderViewModel._currentOrder.value = order
                }
            }
            parentFragmentManager.beginTransaction()
                .remove(this)
                .commit()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): BottomSheetOrderRatingFragment {
            return BottomSheetOrderRatingFragment()
        }
    }
}