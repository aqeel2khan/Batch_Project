package com.dev.batchfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.ChosenMealDetailActivity
import com.dev.batchfinal.databinding.ItemReviewListBinding

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