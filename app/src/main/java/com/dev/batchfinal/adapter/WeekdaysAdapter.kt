package com.dev.batchfinal.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ItemWeeksdayDataBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener

class WeekdaysAdapter(val requireActivity: FragmentActivity, val dates: Array<String?>, val days: Array<String?>, var backDate: String?,
                      var mealPlan: String,
                      var currentDate: String?, val listener: PositionItemClickListener<Int>
) : RecyclerView.Adapter<WeekdaysAdapter.ViewHolder>() {
    private var selectedItemPosition: Int = -1
    private var showCurrentValue: Boolean = true

    inner class ViewHolder(val binding: ItemWeeksdayDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dates: String, days: String?, position: Int) {
//            if (backDate == ""){
                val items1: List<String> = dates.split("-")
                val y1 = items1[2]
                val day = y1.toInt()
                binding.tvDate.text = y1
                binding.tvDays.text = days
//            }else{
//                val item2: List<String> = backDate!!.split("-")
//                val bkDt = item2[2]
//                binding.tvDate.text = bkDt
//                binding.tvDays.text = days
//            }

            binding.root.setOnClickListener {
                selectedItemPosition = position
                notifyDataSetChanged()
                showCurrentValue=false
                listener.onPositionItemSelected(dates, position)
            }
            if (backDate!!.isNotEmpty()){
                currentDate = ""
            }
            if ((currentDate == dates && showCurrentValue) || (backDate == dates && showCurrentValue)) {
                binding.tvDate.background = requireActivity.resources.getDrawable(R.drawable.date_bg_circle)
            } else {
                if (selectedItemPosition == position)
                    binding.tvDate.background = requireActivity.resources.getDrawable(R.drawable.date_bg_circle)
                else
                    if (mealPlan == "mealPlan"){
                        binding.tvDate.setBackgroundColor(Color.parseColor("#CCE1DF"))
                    }else{
                        binding.tvDate.setBackgroundColor(Color.parseColor("#FFFFFF"))
                    }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemWeeksdayDataBinding = ItemWeeksdayDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dates!![position]!!, days[position], position)
    }

    override fun getItemCount(): Int {
        return dates.size
    }
}