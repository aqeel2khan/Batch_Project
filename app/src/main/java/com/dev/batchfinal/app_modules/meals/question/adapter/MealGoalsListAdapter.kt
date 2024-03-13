package com.dev.batchfinal.app_modules.question.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoals
import com.dev.batchfinal.databinding.ItemMealGoalsBinding
import com.dev.batchfinal.`interface`.MealGoalsListItemPosition

class MealGoalsListAdapter(val context: Context?, var mealGoalsList: List<MealGoals>, var listener: MealGoalsListItemPosition<Int>) : RecyclerView.Adapter<MealGoalsListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMealGoalsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mealGoalsData: List<MealGoals>, position: Int) {

            binding.tvMealGoals.text = mealGoalsData[position].name
            if(mealGoalsData[position].selected=="1"){
                binding.tvMealGoals.setBackgroundResource(R.drawable.cmfeet_bg)
            }else{
                binding.tvMealGoals.setBackgroundResource(R.drawable.rectangle_button_gry)
            }



            binding.root.setOnClickListener {
                listener.onMealGoalsListItemPosition(mealGoalsData, position)
                if(mealGoalsList[position].selected=="0" || mealGoalsList[position].selected==null){
                    for (i in 0 until mealGoalsList.size)
                        if (position==i){
                            mealGoalsList[i].selected="1"
                            notifyDataSetChanged()
                        }else{
                            mealGoalsList[i].selected="0"
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()
                }
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