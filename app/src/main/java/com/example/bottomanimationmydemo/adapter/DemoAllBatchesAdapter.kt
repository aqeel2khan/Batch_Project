package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.DemoItemAllBatchesBinding
import com.example.bottomanimationmydemo.databinding.ItemAllBatchesBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.utils.MyConstant.IMAGE_BASE_URL
import com.example.bottomanimationmydemo.utils.MyUtils
import java.util.ArrayList

class DemoAllBatchesAdapter(val context: Context?, var courseList: ArrayList<String>, var listener: PositionItemClickListener<Int>) : RecyclerView.Adapter<DemoAllBatchesAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: DemoItemAllBatchesBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(courseData: String, position: Int) {

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(courseData, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: DemoItemAllBatchesBinding = DemoItemAllBatchesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseList[position], position)
    }
}