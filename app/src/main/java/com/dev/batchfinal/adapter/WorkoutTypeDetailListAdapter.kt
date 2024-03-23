package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.workout_motivator.model.course_details.CourseDetailResponseModel
import com.dev.batchfinal.databinding.ItemWorkoutTypeListBinding

class WorkoutTypeDetailListAdapter(val context: Context?, val typeList: List<CourseDetailResponseModel.WorkoutType>) : RecyclerView.Adapter<WorkoutTypeDetailListAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeListBinding) : RecyclerView.ViewHolder(binding.root){
         fun bind(typeList: CourseDetailResponseModel.WorkoutType, position: Int) {
             binding.typeName.text = typeList.workoutdetail.workoutType
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutTypeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return typeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(typeList[position], position)
    }
}