package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemAllTypeMealBinding
import com.dev.batchfinal.`interface`.MealDishListItemPosition
import com.dev.batchfinal.model.meal_dish_model.MealDishData

class AllTypeOfMealAdapter(
    val requireContext: Context,
    var mealDishList: List<MealDishData>,
    var listener: MealDishListItemPosition<Int>
) : RecyclerView.Adapter<AllTypeOfMealAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllTypeMealBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mealDishList: MealDishData, position: Int) {
            /*      Glide.with(requireContext).load(courseImg[position])
                      .placeholder(
                          R.drawable.profile_girl
                      ).into(binding.imgTrainerProfile)*/

            binding.productName.text = mealDishList.name
            binding.root.setOnClickListener {
                listener.onMealDishListItemPosition(mealDishList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAllTypeMealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealDishList[position], position)
    }

    override fun getItemCount(): Int {
        return mealDishList.size
    }

//    interface isCheckedListener<T> {
//        fun onIsChecked(item: DataItem, postions: Int)
//    }
//
//    interface isUnCheckedListener<T> {
//        fun onIsUnChecked(item: DataItem, postions: Int)
//    }
}