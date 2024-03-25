package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.workout_motivator.model.course_details.CourseDetailResponseModel
import com.dev.batchfinal.databinding.ItemWorkoutTypeBinding


class BatchWorkoutTypeAdapter(val context: Context?, var workout: List<CourseDetailResponseModel.Workout>) : RecyclerView.Adapter<BatchWorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(courseDuration: CourseDetailResponseModel.Workout, position: Int) {
            if (courseDuration.status.toInt()==0){
                binding.weightLossText.text = "Day Off"
                binding.tvIndex.text =""
                binding.llCal.visibility=View.GONE

            }else{
                binding.llCal.visibility=View.VISIBLE

                binding.weightLossText.text = courseDuration.dayName
                binding.tvBoost.text = courseDuration.calorieBurn
                binding.tvBoostMin.text = courseDuration.workoutTime
                binding.tvIndex.text = (position+1).toString()
            }

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
        return workout.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(workout[position], position)
    }
}