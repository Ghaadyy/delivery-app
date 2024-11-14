package com.example.deliveryapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deliveryapp.models.Restaurant

class CustomAdapter(private val dataSet: Array<Restaurant>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantNameTv: TextView = view.findViewById(R.id.restaurant_name_tv)
        val locationTv: TextView = view.findViewById(R.id.location_tv)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.restaurant_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.restaurantNameTv.text = dataSet[position].name
        viewHolder.locationTv.text = dataSet[position].location
    }

    override fun getItemCount() = dataSet.size
}