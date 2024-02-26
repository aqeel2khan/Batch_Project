package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemMealBatchesBinding
import com.example.bottomanimationmydemo.`interface`.MealListItemPosition
import com.example.bottomanimationmydemo.model.meal_list.MealList

class MealBatchPlanAdapter(val context: Context?, var mealList: List<MealList>, var listener: MealListItemPosition<Int>) : RecyclerView.Adapter<MealBatchPlanAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemMealBatchesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mealList: MealList, position: Int) {
            binding.tvMealName.text = mealList.name
            binding.tvMealPrice.text = mealList.price
            binding.tvKcal.text = mealList.avg_cal_per_day
//            binding.tvMealCount.text = mealList.

            binding.root.setOnClickListener {
                listener.onMealListItemPosition(mealList, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealList[position], position)
    }
}