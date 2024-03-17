package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.databinding.ItemDashboardMotivatorBinding
import com.dev.batchfinal.`interface`.CoachListItemPosition
import com.dev.batchfinal.model.coach_list_model.Data

class DashboardMotivatorAdapter(
    val requireContext: Context,
    var coachList: List<Data>,
    var screen:String,
    var listener: CoachListItemPosition<Int>
) : RecyclerView.Adapter<DashboardMotivatorAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemDashboardMotivatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Data, position: Int) {
            binding.txtTrainerName.text = data.name
            binding.txtProfesion.text = "Yoga, pilates"
            MyUtils.loadImage(
                binding.imgTrainerProfile,
                MyConstant.IMAGE_BASE_URL + data.profilePhotoPath
            )

            binding.root.setOnClickListener {
                listener.onCoachListItemPosition(data, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDashboardMotivatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        when (screen) {
            "motivator_home" -> {
                return 5
            }
            else -> {
                return coachList.size

            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coachList[position], position)
    }
}