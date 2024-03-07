package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemListMotivatorBinding
import com.dev.batchfinal.`interface`.CoachListItemPosition
import com.dev.batchfinal.model.coach_list_model.Data
import com.dev.batchfinal.utils.MyConstant
import com.dev.batchfinal.utils.MyUtils

class MotivatorListAdapter(
    val requireContext: Context,
    var coachList: List<Data>,
    var listener: CoachListItemPosition<Int>
) : RecyclerView.Adapter<MotivatorListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemListMotivatorBinding) :
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
            ItemListMotivatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(coachList[position], position)
    }
}