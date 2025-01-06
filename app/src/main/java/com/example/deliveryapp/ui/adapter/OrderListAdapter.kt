package com.example.deliveryapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order
import com.example.deliveryapp.ui.components.order.OrderItem
import com.example.deliveryapp.ui.fragments.order.OrderDetailFragment
import com.example.deliveryapp.ui.viewModel.OrderViewModel

class OrderListAdapter(private val _context: Context, private var _orders: List<Order>, private val _orderViewModel: OrderViewModel) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val composeView: ComposeView = view.findViewById(R.id.orderComposeView)
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
        holder.composeView.setContent {
            OrderItem(order) {
            _orderViewModel.setCurrentOrder(order)
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
}