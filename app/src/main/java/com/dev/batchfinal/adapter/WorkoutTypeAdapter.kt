package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.activity.WeightLossActivity
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDuration
import com.dev.batchfinal.databinding.ItemWorkoutTypeBinding
import com.dev.batchfinal.`interface`.PositionCourseWorkoutClick


class WorkoutTypeAdapter(
    val context: Context?,
    var courseDuration: MutableList<CourseDuration>?,
    var todayDuration: Long,
    val listener: PositionCourseWorkoutClick<Int>
) : RecyclerView.Adapter<WorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(mCoursedurationData: CourseDuration?, position: Int) {
            try {
                val pos = position + 1
                if(mCoursedurationData!=null){
                    if (mCoursedurationData.status?.toInt() ==0){
                        binding.weightLossText.text="Day Off"
                        binding.tvIndex.text = ""
                        binding.llCal.visibility=View.GONE

                    }else{
                        if (!mCoursedurationData.dayName.isNullOrEmpty())
                            binding.weightLossText.text = mCoursedurationData.dayName
                        if (!mCoursedurationData.calorieBurn.isNullOrEmpty())
                            binding.tvBoost.text = mCoursedurationData.calorieBurn.toString()
                        if (!mCoursedurationData.workoutTime.isNullOrEmpty())
                            binding.tvBoostMin.text = mCoursedurationData.workoutTime.toString()
                        binding.tvIndex.text = "$pos"

                    }

                }
                if (todayDuration.toString() == pos.toString()){
                    binding.cardBg.setBackgroundResource(R.drawable.card_bg_select)
                    binding.tvIndex.setTextColor(ContextCompat.getColor(context!!,R.color.light_gry))
                }else{
                    binding.cardBg.setBackgroundResource(R.drawable.gry_ractangle)
                }

                binding.root.setOnClickListener {
                    listener.onCourseWorkoutItemPosition(mCoursedurationData!!, position)
                }
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseDuration?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var mCourse_duration: CourseDuration? =  courseDuration?.get(position)
        if(mCourse_duration!=null){
            holder.bind(mCourse_duration , position)
        }

    }
}