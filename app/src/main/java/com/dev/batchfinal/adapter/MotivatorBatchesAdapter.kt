package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemMotivatorBatchesBinding
import com.dev.batchfinal.`interface`.CourseListItemPosition
import com.dev.batchfinal.model.course_model.ListData
import com.dev.batchfinal.app_utils.MyConstant.IMAGE_BASE_URL
import com.dev.batchfinal.app_utils.MyUtils
import kotlin.collections.ArrayList

class MotivatorBatchesAdapter(val context: Context?, var courseList: ArrayList<ListData>, var listener: CourseListItemPosition<Int>) : RecyclerView.Adapter<MotivatorBatchesAdapter.ViewHolder>(){

    inner class ViewHolder(val binding: ItemMotivatorBatchesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(courseData: ListData, position: Int) {

            MyUtils.loadBackgroundImage(binding.backgroundImg, IMAGE_BASE_URL+courseData.courseImage)

            binding.tvCourseName.text = courseData.courseName
            binding.tvCoursePrice.text = courseData.coursePrice+" KWD"
            val workoutType = courseData.workoutType
            for (i in workoutType){
                binding.workType.text = i.workoutdetail.workoutType
            }
            binding.levelName.text = courseData.courseLevel.levelName
            binding.coachName.text = courseData.coachDetail.name
            MyUtils.loadImage(
                binding.coachProfile,
                IMAGE_BASE_URL+courseData.coachDetail.profilePhotoPath
            )


            binding.root.setOnClickListener {
                listener.onCourseListItemPosition(courseData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMotivatorBatchesBinding = ItemMotivatorBatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList[position], position)
    }

    fun setdata(data: ArrayList<ListData>) {
        courseList!!.addAll(data)
        notifyDataSetChanged()
    }
    fun clearData() {
        courseList!!.clear()
        notifyDataSetChanged()
    }
}