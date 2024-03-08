package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemAllCourseOrderBinding
import com.dev.batchfinal.`interface`.CourseOrderListItemPosition
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils

class CourseOrderAdapter(
    val context: Context?,
    var courseList: ArrayList<OrderList>,
    var listener: CourseOrderListItemPosition<Int>
) : RecyclerView.Adapter<CourseOrderAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemAllCourseOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(courseData: OrderList, position: Int) {

            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData.course_detail.course_image
            )

            binding.txtValidity.text =
                '0' + "/" + courseData.course_detail.course_validity.toString()
            binding.txtCourseDuration.text = courseData.course_detail.duration.toString()
            binding.tvWorkoutName.text = courseData.course_detail.course_name.toString()

//            if (courseData.course_detail.course_duration.isEmpty()) {
//                binding.tvCal.text =
//                    courseData.course_detail.course_duration[position].calorie_burn.toString()
//            } else {
//                binding.tvCal.text = "0.0"
//            }


            binding.root.setOnClickListener {
                listener.onCourseOrderListItemPosition(courseData, position)
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