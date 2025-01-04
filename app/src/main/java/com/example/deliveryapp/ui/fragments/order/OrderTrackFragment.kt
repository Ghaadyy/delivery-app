package com.example.deliveryapp.ui.fragments.order

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Driver
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.data.model.OrderDetail
import com.example.deliveryapp.data.model.OrderStatus
import com.example.deliveryapp.databinding.FragmentOrderTrackBinding
import com.example.deliveryapp.ui.viewModel.OrderViewModel
import com.example.deliveryapp.ui.adapter.OrderTrackAdapter
import com.example.deliveryapp.ui.fragments.bottomsheet.BottomSheetDriverInfoFragment
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class OrderTrackFragment : Fragment(R.layout.fragment_order_track) {
    private lateinit var orderTrackAdapter: OrderTrackAdapter
    private lateinit var orderViewModel: OrderViewModel
    private var _binding: FragmentOrderTrackBinding? = null
    private val binding get() = _binding!!
    private lateinit var mapView: MapView
    private lateinit var marker: Marker
    private lateinit var mapController: IMapController

    private val title = "Beirut"
    private val location = GeoPoint(33.8938, 35.5018)
    private val zoom = 16.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_track, container, false)

        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        Configuration.getInstance().load(context, context?.getSharedPreferences("osmdroid", Context.MODE_PRIVATE))

        mapView = view.findViewById(R.id.mapView)

        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setMultiTouchControls(true)

        mapController = mapView.controller
        mapController.setZoom(zoom)
        mapController.setCenter(location)

        //Add store location
        marker = Marker(mapView)
        marker.position = location
        marker.title = title
        mapView.overlays.add(marker)

        //Add user location
        val userLocation = orderViewModel.currentOrder.value?.orderAddress
        marker = Marker(mapView)
        if (userLocation != null) {
            marker.position = GeoPoint(userLocation.latitude, userLocation.longitude)
        }
        marker.title = "User Location"
        mapView.overlays.add(marker)


        return view
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentOrderTrackBinding.bind(view)

        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.orderSummaryRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        orderViewModel.currentOrder.observe(viewLifecycleOwner) { order ->
            bindDriverInfoCard(order.driver)
            bindOrderDetailsCard(order)
        }

        orderViewModel.currentOrderDetails.observe(viewLifecycleOwner) {
            bindOrderSummary(it)
        }

        binding.driverInfo.setOnClickListener {
            val bottomSheetFragment = BottomSheetDriverInfoFragment.newInstance()
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

    private fun bindDriverInfoCard(driver: Driver?){
        if(driver == null){
            binding.driverInfo.visibility = View.GONE
        }else{
            binding.driverName.text = driver.name
        }
    }

    private fun bindOrderDetailsCard(order: Order) {
        val restaurant = order.restaurantId
        val status = OrderStatus.fromId(order.orderStatus)
        val message = if (status == OrderStatus.DELIVERED) {
            "${status.label} on ${order.deliveredDate}"
        } else {
            "Placed at ${order.orderDate}"
        }
        val trackOrderTopMessage = status?.label
        val totalPrice = "USD ${order.subtotal + order.deliveryCharge - order.discountedPrice}"

        binding.restaurantId.text = restaurant
        binding.status.text = message
        binding.trackOrderStatus.text = trackOrderTopMessage
        binding.total.text = totalPrice
    }

    private fun bindOrderSummary(orderDetails: List<OrderDetail>) {
        orderTrackAdapter = OrderTrackAdapter(orderDetails)
        binding.orderSummaryRecyclerView.adapter = orderTrackAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapView.onDetach()
        _binding = null
    }
}
