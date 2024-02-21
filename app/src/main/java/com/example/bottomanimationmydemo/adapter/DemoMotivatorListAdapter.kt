package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.DemoItemListMotivatorBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.utils.MyUtils
import java.util.ArrayList

class DemoMotivatorListAdapter(
    var requireContext: Context,
    var courseImg: ArrayList<Int>,
    var techerName: ArrayList<String>,
    var profesion: ArrayList<String>,
    var listener: PositionItemClickListener<Int>
) : RecyclerView.Adapter<DemoMotivatorListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: DemoItemListMotivatorBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String, profile: Int, position: Int) {
            binding.txtTrainerName.text = data
            binding.txtProfesion.text = profesion[position]
            Glide.with(requireContext).load(courseImg)
                .placeholder(
                    R.drawable.avtar
                ).into(binding.imgTrainerProfile)

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(data, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DemoItemListMotivatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return techerName.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(techerName[position],courseImg[position], position)
    }
}