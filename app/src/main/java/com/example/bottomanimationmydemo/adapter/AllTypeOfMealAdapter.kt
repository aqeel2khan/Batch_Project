package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ItemAllTypeMealBinding
import com.example.bottomanimationmydemo.databinding.ItemListMotivatorBinding
import com.example.bottomanimationmydemo.databinding.ItemListRecomdProductBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import java.util.ArrayList

class AllTypeOfMealAdapter(
    val requireContext: Context,
    var name: ArrayList<String>,
    var listener: PositionItemClickListener<Int>) : RecyclerView.Adapter<AllTypeOfMealAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllTypeMealBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, position: Int) {
      /*      Glide.with(requireContext).load(courseImg[position])
                .placeholder(
                    R.drawable.profile_girl
                ).into(binding.imgTrainerProfile)*/

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTypeOfMealAdapter.ViewHolder {
        val binding = ItemAllTypeMealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllTypeOfMealAdapter.ViewHolder, position: Int) {
        holder.bind(name[position], position)
    }

    override fun getItemCount(): Int {
        return name.size
    }

//    interface isCheckedListener<T> {
//        fun onIsChecked(item: DataItem, postions: Int)
//    }
//
//    interface isUnCheckedListener<T> {
//        fun onIsUnChecked(item: DataItem, postions: Int)
//    }
}