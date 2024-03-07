package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemChosenMealListBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import java.util.ArrayList


class ChosenMealListAdapter(
    val context: Context?,
    val mealName: ArrayList<String>,
    val listener: PositionItemClickListener<Int>
) : RecyclerView.Adapter<ChosenMealListAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemChosenMealListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: String, position: Int) {
            binding.txtMealName.text = name
            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChosenMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealName.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealName[position], position)
    }
}