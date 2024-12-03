package com.example.deliveryapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.ui.fragments.order.OrderDetailFragment
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class OrderListAdapter(private val _context: Context, private var _orders: List<Order>, private val _orderViewModel: OrderViewModel) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val restaurantId: TextView = view.findViewById(R.id.restaurantId)
        val status: TextView = view.findViewById(R.id.status)
        val orderTotalPrice: TextView = view.findViewById(R.id.orderTotalPrice)
        var arrow: ImageView = view.findViewById(R.id.arrowIcon)
    }

    fun updateOrders(orders: List<Order>){
        _orders = orders
        notifyDataSetChanged() //TODO("Fix this issue")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_list_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _orders.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = _orders[position]
        holder.restaurantId.text = order.restaurantId
        val status = order.orderStatus
        val message = if(status?.id == 6){
            "${status.label} on ${order.deliveredDate}"
        }else{
            status?.label
        }
        holder.status.text = message
        val totalPrice = "Total Price: ${order.subtotal + order.deliveryCharge - order.discountedPrice}";
        holder.orderTotalPrice.text = totalPrice

        holder.arrow.setOnClickListener {
            _orderViewModel._currentOrder.value = order
            Log.d("OrderListAdapter", order.toString())
            _orderViewModel.getSelectedOrderDetails(order.id)

            val orderDetailFragment = OrderDetailFragment()
            val transaction = (_context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction
                .replace(R.id.fragment_container, orderDetailFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}