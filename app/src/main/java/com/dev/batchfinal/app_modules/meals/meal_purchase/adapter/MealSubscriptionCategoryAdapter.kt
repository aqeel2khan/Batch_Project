package com.dev.batchfinal.app_modules.meals.meal_purchase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.`interface`.MealSubscriptionCategoryListItemPosition
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_subscription_details_model.MealSubscriptionCategoryResponse
import com.dev.batchfinal.databinding.ItemMealPlanBinding


class MealSubscriptionCategoryAdapter(
    val context: Context?, var mealSubscriptionCategoryResponse: MutableList<MealSubscriptionCategoryResponse>, var listner: MealSubscriptionCategoryListItemPosition<Int>
) : RecyclerView.Adapter<MealSubscriptionCategoryAdapter.ViewHolder>(){
    private var previousPosition = 0

    inner class ViewHolder(val binding: ItemMealPlanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: MutableList<MealSubscriptionCategoryResponse>, position: Int) {
            binding.tvMealName.text = category[position].categoryName
            if (position==previousPosition){
                previousPosition=-1
                binding.llBreakfast.setBackgroundResource(R.drawable.button_selected)
            }else{
                if(category[position].selected==true){
                    binding.llBreakfast.setBackgroundResource(R.drawable.button_selected)
                }else{
                    binding.llBreakfast.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                }
            }
            binding.root.setOnClickListener {
                listner.onMealSubscriptionCategoryListItemPosition(category, position)
                if(category[position].selected==false){
                    for (i in 0 until mealSubscriptionCategoryResponse.size)
                        if (position==i){
                            category[i].selected=true
                            notifyDataSetChanged()
                        }else{
                            category[i].selected=false
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()

                }




/*                    if(category[position].categoryName=="Breakfast"){
                    category[position].selected=true

                    notifyDataSetChanged()
                }else{
                        category[position].selected=false

                    }



                    if(category[position].categoryName=="Dinner"){
                    category[position].selected=true

                    notifyDataSetChanged()
                }else if(category[position].categoryName=="Snack"){
                    category[position].selected=true

                    notifyDataSetChanged()
                }else{
                    category[position].selected=false

                }*/




            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealSubscriptionCategoryResponse.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealSubscriptionCategoryResponse, position)
    }
}