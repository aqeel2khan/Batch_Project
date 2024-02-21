package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ItemAllWorkoutVideoBinding
import java.util.ArrayList

class AllWorkoutVideoListAdapter(
    var requireContext: Context,
    var courseImg: ArrayList<Int>,
) : RecyclerView.Adapter<AllWorkoutVideoListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllWorkoutVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Int, position: Int) {
            Glide.with(requireContext).load(courseImg[position])
                .placeholder(
                    R.drawable.exercise_image
                ).into(binding.ivEmployeeImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAllWorkoutVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseImg.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseImg[position], position)
    }
}