package com.dev.batchfinal.app_modules.account.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_modules.account.minterface.FollowingCallback
import com.dev.batchfinal.app_modules.account.model.MotivatorFollowingData
import com.dev.batchfinal.databinding.ItemFollowingListBinding
import com.dev.batchfinal.app_utils.MyUtils
import java.util.ArrayList

class FollowingListAdapter(val mContext: Context, val motivatorFollowingList: ArrayList<MotivatorFollowingData>, var mListner: FollowingCallback) : RecyclerView.Adapter<FollowingListAdapter.ViewHolder>() {
    init {
        this.mListner=mListner
    }
    inner class ViewHolder(val binding: ItemFollowingListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MotivatorFollowingData, position: Int) {
            MyUtils.loadImage(binding.profileImage, data.profilePhotoPath)
            if(!data.name.isNullOrEmpty())
            {
                binding.txtTrainerName.text=""+data.name

            }else
            {
                binding.txtTrainerName.text="Trainer Name"

            }
           //NOTE-Trainer profession not coming on response payload need to add


            binding.btnFollowing.setOnClickListener {
               // binding.btnFollow.visibility = View.VISIBLE
               // binding.btnFollowing.visibility = View.GONE
                mListner.onClickMotivator(position,data)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFollowingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return motivatorFollowingList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(motivatorFollowingList[position], position)
    }
}