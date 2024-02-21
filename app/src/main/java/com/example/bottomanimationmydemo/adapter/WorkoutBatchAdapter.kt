package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemWorkoutBatchBinding
import com.example.bottomanimationmydemo.`interface`.CourseListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.course_model.ListData
import java.util.ArrayList


class WorkoutBatchAdapter(val context: Context?, var courseList: ArrayList<ListData>, val lister: CourseListItemPosition<Int>) : RecyclerView.Adapter<WorkoutBatchAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutBatchBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: ListData, position: Int) {
//            binding.workoutType.text = name
            binding.root.setOnClickListener {
                lister.onCourseListItemPosition(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutBatchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList[position], position)
    }
}