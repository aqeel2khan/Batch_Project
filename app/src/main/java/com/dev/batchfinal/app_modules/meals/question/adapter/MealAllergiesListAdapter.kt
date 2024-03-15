package com.dev.batchfinal.app_modules.question.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.question.model.meal_allergies.MealAllergies
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.databinding.ItemMealAllergiesListBinding
import com.dev.batchfinal.`interface`.MealAllergiesListItemPosition

class MealAllergiesListAdapter(val context: Context?, var allergiesList: List<MealAllergies>, var listener: MealAllergiesListItemPosition<Int>) : RecyclerView.Adapter<MealAllergiesListAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMealAllergiesListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mealAllergiesList: List<MealAllergies>, position: Int) {

            binding.tvAllergiesName.text = mealAllergiesList[position].name
            MyUtils.loadImage(
                binding.allergiesIcon,
                MyConstant.IMAGE_BASE_URL + mealAllergiesList[position].icon
            )
            if(mealAllergiesList[position].selected=="1"){
                    binding.cardBg.setBackgroundResource(R.drawable.cmfeet_bg)
                }else{
                    binding.cardBg.setBackgroundResource(R.drawable.rectangle_button_gry)
                }

            binding.root.setOnClickListener {
                listener.onMealAllergiesListItemPosition(mealAllergiesList, position)
//                mealAllergiesList[position].selected == "1"
                if(mealAllergiesList[position].selected=="0" || mealAllergiesList[position].selected==null){
                    for (i in 0 until mealAllergiesList.size)
                        if (position==i){
                            mealAllergiesList[i].selected="1"
                            notifyDataSetChanged()
                        }else{
                            mealAllergiesList[i].selected="0"
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMealAllergiesListBinding = ItemMealAllergiesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return allergiesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allergiesList, position)
    }
}