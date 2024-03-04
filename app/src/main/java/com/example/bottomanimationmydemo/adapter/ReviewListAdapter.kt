package com.example.bottomanimationmydemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemReviewListBinding
import com.example.bottomanimationmydemo.model.chosen_meal_details_model.ChosenMealDetailsResponse.NutritionDetail
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData
import com.example.bottomanimationmydemo.view.activity.ChosenMealDetailActivity

class ReviewListAdapter(val chosenMealDetailActivity: ChosenMealDetailActivity) : RecyclerView.Adapter<ReviewListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemReviewListBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }
}