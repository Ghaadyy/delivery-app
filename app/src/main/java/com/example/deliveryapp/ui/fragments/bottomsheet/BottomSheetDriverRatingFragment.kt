package com.example.deliveryapp.ui.fragments.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.deliveryapp.data.model.DriverRating
import com.example.deliveryapp.databinding.FragmentBottomSheetDriverRatingBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetDriverRatingFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentBottomSheetDriverRatingBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderViewModel: OrderViewModel
    private lateinit var selectedDriverRating: DriverRating

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDriverRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        binding.likeButton.setOnClickListener {
            selectedDriverRating = DriverRating.LIKE
        }

        binding.dislikeButton.setOnClickListener {
            selectedDriverRating = DriverRating.DISLIKE
        }

        binding.submitRatingButton.setOnClickListener{
            val currentOrder = orderViewModel.currentOrder.value
            if(currentOrder?.driverRating == DriverRating.PENDING.ordinal &&
                selectedDriverRating != DriverRating.PENDING &&
                selectedDriverRating != DriverRating.NOT_APPLICABLE){

                currentOrder.let { order ->
                    order.driverRating = selectedDriverRating.ratingValue
                    orderViewModel.setCurrentOrder(order)
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
        fun newInstance(): BottomSheetDriverRatingFragment {
            return BottomSheetDriverRatingFragment()
        }
    }
}