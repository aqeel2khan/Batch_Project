package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemMealPlanBinding
import com.example.bottomanimationmydemo.databinding.ItemWorkoutTypeBinding
import com.example.bottomanimationmydemo.`interface`.CategoryListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.meal_detail_model.Category
import java.util.ArrayList

class MealPlanListAdapter(val context: Context?, var category: List<Category>, var listner: CategoryListItemPosition<Int>
) : RecyclerView.Adapter<MealPlanListAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemMealPlanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: Category, position: Int) {
            binding.tvMealName.text = category.categoryName
            binding.root.setOnClickListener {
                listner.onCategoryListItemPosition(category, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return category.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(category[position], position)
    }
}