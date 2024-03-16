package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ItemMealPlanBinding
import com.dev.batchfinal.`interface`.CategoryListItemPosition
import com.dev.batchfinal.model.meal_detail_model.Category

class MealPlanListAdapter(val context: Context?, var category: List<Category>, var listner: CategoryListItemPosition<Int>
) : RecyclerView.Adapter<MealPlanListAdapter.ViewHolder>(){
    private var previousPosition = 0

    inner class ViewHolder(val binding: ItemMealPlanBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(category: List<Category>, position: Int) {
            binding.tvMealName.text = category[position].categoryName
            if (position==previousPosition){
                previousPosition=-1
                binding.llBreakfast.setBackgroundResource(R.drawable.button_selected)
            }else{
                if(category[position].isSelected==true){
                    binding.llBreakfast.setBackgroundResource(R.drawable.button_selected)
                }else{
                    binding.llBreakfast.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                }
            }

            binding.root.setOnClickListener {
                listner.onCategoryListItemPosition(category[position], position)
                if(category[position].isSelected==false ){
                    for (i in 0 until category.size)
                        if (position==i){
                            category[i].isSelected=true
                            notifyDataSetChanged()
                        }else{
                            category[i].isSelected=false
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()

                }
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
        holder.bind(category, position)
    }
}