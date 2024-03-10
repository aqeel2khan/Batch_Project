package com.dev.batchfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.ChosenMealDetailActivity
import com.dev.batchfinal.databinding.ItemDishNutritionListBinding
import com.dev.batchfinal.model.chosen_meal_details_model.ChosenMealDetailsResponse


class NutritionListAdapter(val chosenMealDetailActivity: ChosenMealDetailActivity, var nutritionDetailList: List<ChosenMealDetailsResponse.NutritionDetail>) : RecyclerView.Adapter<NutritionListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDishNutritionListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(nutritionDetailList: ChosenMealDetailsResponse.NutritionDetail, position: Int) {
            binding.nutritionValue.text = nutritionDetailList.value
            binding.nutritionName.text = nutritionDetailList.nutrientName
            binding.root.setOnClickListener {
               // listener.onMealDishListItemPosition(mealDishList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDishNutritionListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nutritionDetailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nutritionDetailList[position], position)

    }
}