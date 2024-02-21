package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemMealPlanBinding
import com.example.bottomanimationmydemo.databinding.ItemWorkoutTypeBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import java.util.ArrayList


class MealPlanListAdapter(
    val context: Context?,
    var name: ArrayList<String>,
    var listner: PositionItemClickListener<Int>
) : RecyclerView.Adapter<MealPlanListAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemMealPlanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, position: Int) {
            binding.tvMealName.text = name
            binding.root.setOnClickListener {
                listner.onPositionItemSelected(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(name[position], position)
    }
}