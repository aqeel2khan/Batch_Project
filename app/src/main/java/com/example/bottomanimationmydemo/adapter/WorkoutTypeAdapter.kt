package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemWorkoutTypeBinding
import com.example.bottomanimationmydemo.`interface`.PositionCourseWorkoutClick
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration


class WorkoutTypeAdapter(val context: Context?,var courseDuration: ArrayList<Course_duration>, val listener: PositionCourseWorkoutClick<Int>) : RecyclerView.Adapter<WorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: Course_duration, position: Int) {
            binding.weightLossText.text = name.day_name
            binding.tvBoost.text = name.calorie_burn.toString()
            binding.tvBoostMin.text = name.workout_time.toString()
            for (i in name.course_duration_exercise){
//                binding.tvIndex.text =
            }
//             MyUtils.loadBackgroundImage(binding.ivEmployeeImage, MyConstant.IMAGE_BASE_URL +courseDuration.get(position).calorie_burn)

            binding.root.setOnClickListener {
                listener.onCourseWorkoutItemPosition(name, position)
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