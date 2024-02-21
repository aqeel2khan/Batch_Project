package com.example.bottomanimationmydemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemAllCourseOrderBinding
import com.example.bottomanimationmydemo.`interface`.CourseOrderListItemPosition
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyUtils

class CourseOrderAdapter(val context: Context?, var courseList: ArrayList<OrderList>, var listener: CourseOrderListItemPosition<Int>) : RecyclerView.Adapter<CourseOrderAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemAllCourseOrderBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(courseData: OrderList, position: Int) {

            MyUtils.loadBackgroundImage(binding.backgroundImg, MyConstant.IMAGE_BASE_URL +courseData.course_detail.course_image)

            binding.txtValidity.text = '0'+"/"+courseData.course_detail.course_validity.toString()
            binding.txtCourseDuration.text = courseData.course_detail.duration.toString()
            binding.tvWorkoutName.text = courseData.course_detail.course_name.toString()

            binding.tvCal.text = courseData.course_detail.course_duration[0].calorie_burn.toString()

            binding.root.setOnClickListener {
                listener.onCourseOrderListItemPosition(courseData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAllCourseOrderBinding = ItemAllCourseOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList[position], position)
    }
}