package com.example.bottomanimationmydemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomanimationmydemo.databinding.ItemWorkoutTypeBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener


class WorkoutTypeAdapter(val context: Context?, val name: ArrayList<String>, val listener: PositionItemClickListener<Int>) : RecyclerView.Adapter<WorkoutTypeAdapter.ViewHolder>(){
    inner class ViewHolder(val binding: ItemWorkoutTypeBinding) : RecyclerView.ViewHolder(binding.root){
         fun bind(name: String, position: Int) {
//             binding.workoutType.text = name
             binding.root.setOnClickListener {
                 listener.onPositionItemSelected(name, position)
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemWorkoutTypeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return name.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(name[position], position)
    }
}