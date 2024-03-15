package com.dev.batchfinal.app_modules.question.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.question.model.meal_tags.MealTags
import com.dev.batchfinal.databinding.ItemMealGoalsBinding
import com.dev.batchfinal.`interface`.MealTagsListItemPosition

class MealTagsListAdapter(val context: Context?, var mealGoalsList: List<MealTags>, var listener: MealTagsListItemPosition<Int>) : RecyclerView.Adapter<MealTagsListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMealGoalsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mealGoalsData: List<MealTags>, position: Int) {

            binding.tvMealGoals.text = mealGoalsData[position].name
            if(mealGoalsData[position].selected=="1"){
                binding.tvMealGoals.setBackgroundResource(R.drawable.cmfeet_bg)
            }else{
                binding.tvMealGoals.setBackgroundResource(R.drawable.rectangle_button_gry)
            }

            binding.root.setOnClickListener {
                listener.onMealTagsListItemPosition(mealGoalsData, position)
                mealGoalsData[position].selected = "1"
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMealGoalsBinding = ItemMealGoalsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealGoalsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealGoalsList, position)
    }
}