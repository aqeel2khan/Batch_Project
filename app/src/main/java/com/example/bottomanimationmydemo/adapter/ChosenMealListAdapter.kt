package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemChosenMealListBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import org.json.JSONObject
import java.util.ArrayList


class ChosenMealListAdapter(
    val context: Context?,
    val mealName: ArrayList<JSONObject>,
    val listener: PositionItemClickListener<Int>
) : RecyclerView.Adapter<ChosenMealListAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemChosenMealListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: JSONObject, position: Int) {
            binding.weightLossText.text = name.getString("dish_name")
            //binding.tvBoost.text = name.getString("dish_name")
           /* binding.txtMealName.text = name
            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChosenMealListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealName.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mealName[position], position)
    }
}