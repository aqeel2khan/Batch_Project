package com.example.bottomanimationmydemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemDishNutritionListBinding
import com.example.bottomanimationmydemo.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData
import com.example.bottomanimationmydemo.view.activity.ChosenMealDetailActivity



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