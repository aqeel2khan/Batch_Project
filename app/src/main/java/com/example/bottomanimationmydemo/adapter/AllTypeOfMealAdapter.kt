package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ItemAllTypeMealBinding
import com.example.bottomanimationmydemo.`interface`.MealDishListItemPosition
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData

class AllTypeOfMealAdapter(
    val requireContext: Context,
    var mealDishLists: List<MealDishData>,
    var screen:String,
    var listener: MealDishListItemPosition<Int>
) : RecyclerView.Adapter<AllTypeOfMealAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllTypeMealBinding): RecyclerView.ViewHolder(binding.root) {
        private var previousPosition = 0

        fun bind(mealDishList: List<MealDishData>, position: Int) {
            /*      Glide.with(requireContext).load(courseImg[position])
                      .placeholder(
                          R.drawable.profile_girl
                      ).into(binding.imgTrainerProfile)*/
            if (screen=="meal_plan"){
                binding.btRadio.visibility=View.VISIBLE
               /* if(position==0){
                    binding.btRadio.setImageResource(R.drawable.selected_icon)
                }else{
                    binding.btRadio.setImageResource(R.drawable.radio_un_sel)

                }*/




            }else{
                binding.btRadio.visibility=View.GONE

            }
            if (position==previousPosition){
                previousPosition=-1
                binding.btRadio.setBackgroundResource(R.drawable.selected_icon)
            }else{
                if(mealDishList[position].selected=="1"){
                    binding.btRadio.setBackgroundResource(R.drawable.selected_icon)
                }else{
                    binding.btRadio.setBackgroundResource(R.drawable.radio_un_sel)
                }
            }

            binding.btRadio.setOnClickListener {
                listener.onMealDishSelectItemPosition(mealDishList, position)
                if(mealDishList[position].selected=="0"){
                    for (i in 0 until mealDishLists.size)
                        if (position==i){
                            mealDishList[i].selected="1"
                            notifyDataSetChanged()
                        }else{
                            mealDishList[i].selected="0"
                            notifyDataSetChanged()
                        }
                }else {
                    notifyDataSetChanged()
                }
            }
            binding.productName.text = mealDishList[position].name
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
        holder.bind(mealDishLists, position)
    }

    override fun getItemCount(): Int {
        return mealDishLists.size
    }

//    interface isCheckedListener<T> {
//        fun onIsChecked(item: DataItem, postions: Int)
//    }
//
//    interface isUnCheckedListener<T> {
//        fun onIsUnChecked(item: DataItem, postions: Int)
//    }
}