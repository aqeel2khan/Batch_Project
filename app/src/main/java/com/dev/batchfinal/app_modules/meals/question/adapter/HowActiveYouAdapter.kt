package com.dev.batchfinal.app_modules.question.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.question.model.HowActiveYouModel
import com.dev.batchfinal.databinding.ItemMealGoalsBinding
import com.dev.batchfinal.`interface`.HowActiveAreListItemPosition

class HowActiveYouAdapter(val context: Context?, var activeList: ArrayList<HowActiveYouModel>, var listener: HowActiveAreListItemPosition<Int>) : RecyclerView.Adapter<HowActiveYouAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMealGoalsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ArrayList<HowActiveYouModel>, position: Int) {

            binding.tvMealGoals.text = list[position].name

            if(list[position].isSelected=="1"){
                binding.tvMealGoals.setBackgroundResource(R.drawable.cmfeet_bg)
            }else{
                binding.tvMealGoals.setBackgroundResource(R.drawable.rectangle_button_gry)
            }

            binding.root.setOnClickListener {
                listener.onHowActiveAreListItemPosition(list[position], position)
                if(list[position].isSelected=="" || list[position].isSelected== null){
                    for (i in 0 until list.size)
                        if (position==i){
                            list[i].isSelected="1"
                            notifyDataSetChanged()
                        }else{
                            list[i].isSelected=""
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
        return activeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(activeList, position)
    }
}