package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ItemAllTypeMealBinding
import com.example.bottomanimationmydemo.databinding.ItemListMotivatorBinding
import com.example.bottomanimationmydemo.databinding.ItemListRecomdProductBinding
import com.example.bottomanimationmydemo.`interface`.MealDishListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData
import java.util.ArrayList

class AllTypeOfMealAdapter(
    val requireContext: Context,
    var mealDishList: List<MealDishData>,
    var screen:String,
    var listener: MealDishListItemPosition<Int>
) : RecyclerView.Adapter<AllTypeOfMealAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllTypeMealBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(mealDishList: MealDishData, position: Int) {
            /*      Glide.with(requireContext).load(courseImg[position])
                      .placeholder(
                          R.drawable.profile_girl
                      ).into(binding.imgTrainerProfile)*/
            if (screen=="meal_plan"){
                binding.btRadio.visibility=View.VISIBLE
            }else{
                binding.btRadio.visibility=View.GONE

            }
            binding.productName.text = mealDishList.name
            binding.root.setOnClickListener {
                listener.onMealDishListItemPosition(mealDishList, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypeOfMealAdapter.ViewHolder {
        val binding = ItemAllTypeMealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllTypeOfMealAdapter.ViewHolder, position: Int) {
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