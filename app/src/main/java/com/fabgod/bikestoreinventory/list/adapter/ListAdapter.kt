package com.fabgod.bikestoreinventory.list.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fabgod.bikestoreinventory.R
import com.fabgod.bikestoreinventory.databinding.BikeItemBinding
import com.fabgod.bikestoreinventory.list.model.Bike
import com.fabgod.bikestoreinventory.utils.toBalanceFormat

class ListAdapter(
    private var context: Context,
    private var bikes: MutableList<Bike>,
    private val onClickListener: (Bike) -> Unit,
) : RecyclerView.Adapter<ListAdapter.ListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.bike_item, parent, false)
        return ListHolder(view)
    }

    override fun getItemCount(): Int = bikes.size

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        val bike = bikes[position]
        with(holder) {
            binding.apply {
                image.setImageResource(bike.imageResource)
                model.text = context.getString(R.string.list_model_format, bike.model)
                color.text = context.getString(R.string.list_color_format, bike.color)
                price.text = context.getString(R.string.list_price_format, bike.price.toBalanceFormat())
                bikeItemLayout.setOnClickListener {
                    onClickListener(bike)
                }
            }
        }
    }

    inner class ListHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = BikeItemBinding.bind(view)
    }
}
