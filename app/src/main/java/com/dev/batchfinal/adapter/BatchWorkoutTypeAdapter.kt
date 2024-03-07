package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemWorkoutTypeBinding
import com.dev.batchfinal.model.course_detail.CourseDuration


class BatchWorkoutTypeAdapter(val context: Context?, var courseDuration: List<CourseDuration>) : RecyclerView.Adapter<BatchWorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(courseDuration: CourseDuration, position: Int) {

            binding.weightLossText.text = courseDuration.dayName
            binding.tvBoost.text = courseDuration.calorieBurn
            binding.tvBoostMin.text = courseDuration.workoutTime
            binding.root.setOnClickListener {
//                lister.onPositionItemSelected(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseDuration.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(courseDuration[position], position)
    }
}