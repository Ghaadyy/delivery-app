package com.example.deliveryapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.R
import com.example.deliveryapp.data.model.OrderDetail

class OrderDetailAdapter(private var _orderDetails: List<OrderDetail>) : RecyclerView.Adapter<OrderDetailAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val itemName: TextView = view.findViewById(R.id.itemName)
        val totalPrice: TextView = view.findViewById(R.id.totalPrice)
        val quantity: TextView = view.findViewById(R.id.itemQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_detail_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return _orderDetails.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderDetail = _orderDetails[position]
        holder.itemName.text = orderDetail.meal.name
        val totalPriceUSD = "USD ${orderDetail.totalPrice}";
        holder.totalPrice.text = totalPriceUSD
        holder.quantity.text = orderDetail.quantity.toString()
    }
}