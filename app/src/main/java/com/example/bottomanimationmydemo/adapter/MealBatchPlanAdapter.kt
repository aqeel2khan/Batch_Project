package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemMealBatchesBinding
import com.example.bottomanimationmydemo.`interface`.MealListItemPosition
import com.example.bottomanimationmydemo.model.meal_list.MealList
import com.example.bottomanimationmydemo.view.activity.FoodPlanBasedOnQuestionActivity
import com.example.bottomanimationmydemo.view.activity.QuestionActivity

class MealBatchPlanAdapter(val context: Context?, var mealList: List<MealList>, var listener: MealListItemPosition<Int>) : RecyclerView.Adapter<MealBatchPlanAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemMealBatchesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(mealList: MealList, position: Int) {
            if (!mealList.name.isNullOrEmpty()){
                binding.tvMealName.text = mealList.name
            }
            if (!mealList.price.isNullOrEmpty()){
                binding.tvMealPrice.text = mealList.price
            }
            binding.tvKcal.text = mealList.avgCalPerDay
            binding.tvMealCount.text = mealList.mealCount.toString()

            if (position  == 2){
                binding.cardCalculate.visibility = View.VISIBLE
            }else{
                binding.cardCalculate.visibility = View.GONE
            }
            binding.btnCalculate.setOnClickListener {
                context!!.startActivity(Intent(context, QuestionActivity::class.java))
//                context!!.startActivity(Intent(context, FoodPlanBasedOnQuestionActivity::class.java))
            }

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