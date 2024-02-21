package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemMealBatchesBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

class MealBatchPlanAdapter(val context: Context?, var name: ArrayList<String>, var listener: PositionItemClickListener<Int>) : RecyclerView.Adapter<MealBatchPlanAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemMealBatchesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, position: Int) {
            binding.tvWorkoutName.text = name

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(name[position], position)
    }
}