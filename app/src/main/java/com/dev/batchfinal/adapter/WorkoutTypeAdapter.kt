package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ItemWorkoutTypeBinding
import com.dev.batchfinal.`interface`.PositionCourseWorkoutClick
import com.dev.batchfinal.model.courseorderlist.Course_duration


class WorkoutTypeAdapter(
    val context: Context?,
    var courseDuration: ArrayList<Course_duration>,
    var todayDuration: String,
    val listener: PositionCourseWorkoutClick<Int>
) : RecyclerView.Adapter<WorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: Course_duration, position: Int) {
            val pos = position + 1
            binding.weightLossText.text = name.day_name
            binding.tvBoost.text = name.calorie_burn.toString()
            binding.tvBoostMin.text = name.workout_time.toString()
            binding.tvIndex.text = "0" +pos

            if (todayDuration.equals(pos.toString())){
                binding.cardBg.setBackgroundResource(R.drawable.card_bg_select)
                binding.tvIndex.setTextColor(ContextCompat.getColor(context!!,R.color.light_gry))
            }else{
                binding.cardBg.setBackgroundResource(R.drawable.rectangle_button_gry)
            }

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