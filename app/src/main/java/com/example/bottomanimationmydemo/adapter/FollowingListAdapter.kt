package com.example.bottomanimationmydemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ItemFollowingListBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.activity.ProfileActivity
import java.util.ArrayList

class FollowingListAdapter(val profileActivity: ProfileActivity, val courseImg: ArrayList<Int>/*, val listener: PositionItemClickListener<Int>*/) : RecyclerView.Adapter<FollowingListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemFollowingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, position: Int) {
            MyUtils.loadImage(binding.profileImage, image.toString())

            binding.btnFollowing.setOnClickListener {
                binding.btnFollow.visibility = View.VISIBLE
                binding.btnFollowing.visibility = View.GONE
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return courseImg.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(courseImg[position], position)
    }
}