package com.example.bottomanimationmydemo.meals.meal_unpurchase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemIngredientsListBinding
import com.example.bottomanimationmydemo.meals.meal_unpurchase.view.activity.ChosenMealDetailActivity


class IngredientsAdapter(val chosenMealDetailActivity: ChosenMealDetailActivity, var ingredientsList: List<String>) : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemIngredientsListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(ingredientsList: String, position: Int) {
            binding.ingredientName.text = ingredientsList

            binding.root.setOnClickListener {
                // listener.onMealDishListItemPosition(mealDishList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemIngredientsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(ingredientsList[position], position)

    }
}