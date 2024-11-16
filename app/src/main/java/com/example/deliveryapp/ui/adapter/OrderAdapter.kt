package com.example.deliveryapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.Order

class OrderAdapter(private val orders: List<Order>) : RecyclerView.Adapter<OrderAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val restaurantId: TextView = view.findViewById(R.id.restaurantId)
        val deliveryDate: TextView = view.findViewById(R.id.deliveryDate)
        val orderTotalPrice: TextView = view.findViewById(R.id.orderTotalPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orders.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val order = orders[position]
        holder.restaurantId.text = order.restaurantId
        val orderDate = "Delivery Date: ${order.orderDate}";
        holder.deliveryDate.text = orderDate
        val totalPrice = "Total Price: ${order.subtotal + order.deliveryCharge - order.discountedPrice}";
        holder.orderTotalPrice.text = totalPrice
    }
}