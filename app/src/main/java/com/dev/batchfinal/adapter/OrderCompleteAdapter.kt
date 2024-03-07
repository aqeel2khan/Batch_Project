package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemOrderCompleteBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import java.util.ArrayList

class OrderCompleteAdapter(val context: Context?, var name: ArrayList<String>, var listener: PositionItemClickListener<Int>) : RecyclerView.Adapter<OrderCompleteAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemOrderCompleteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, position: Int) {
            binding.tvWorkoutName.text = name

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderCompleteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(name[position], position)
    }
}