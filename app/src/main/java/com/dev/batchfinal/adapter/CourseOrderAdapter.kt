package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemAllCourseOrderBinding
import com.dev.batchfinal.`interface`.CourseOrderListItemPosition
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils

class CourseOrderAdapter(
    val context: Context?,
    var courseList: List<com.dev.batchfinal.app_modules.scanning.model.course_order_list.List>,
    var listener: CourseOrderListItemPosition<Int>
) : RecyclerView.Adapter<CourseOrderAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllCourseOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(courseData: com.dev.batchfinal.app_modules.scanning.model.course_order_list.List, position: Int) {

            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData.courseDetail.courseImage
            )

            binding.txtValidity.text =
                '0' + "/" + courseData.courseDetail.courseValidity.toString()
            binding.txtCourseDuration.text = courseData.courseDetail.duration.toString()
            binding.tvWorkoutName.text = courseData.courseDetail.courseName.toString()

//            if (courseData.course_detail.course_duration.isEmpty()) {
//                binding.tvCal.text =
//                    courseData.course_detail.course_duration[position].calorie_burn.toString()
//            } else {
//                binding.tvCal.text = "0.0"
//            }


            binding.root.setOnClickListener {
                try {
                    listener.onCourseOrderListItemPosition(courseData, position)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAllCourseOrderBinding =
            ItemAllCourseOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList[position], position)
    }
}