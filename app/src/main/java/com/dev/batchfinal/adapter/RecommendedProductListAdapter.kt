package com.dev.batchfinal.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.databinding.ItemListRecomdProductBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import java.util.ArrayList

class RecommendedProductListAdapter(
    val requireContext: Context,
    var courseImg: ArrayList<Int>,
    var techerName: ArrayList<String>,
    var profesion: ArrayList<String>,
    var listener: PositionItemClickListener<Int>
) : RecyclerView.Adapter<RecommendedProductListAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemListRecomdProductBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(name: String, position: Int) {
            binding.productName.text = name
            binding.productPrice.text = profesion[position]
      /*      Glide.with(requireContext).load(courseImg[position])
                .placeholder(
                    R.drawable.profile_girl
                ).into(binding.imgTrainerProfile)*/

            binding.root.setOnClickListener {
                listener.onPositionItemSelected(name, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListRecomdProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(techerName[position], position)
    }

    override fun getItemCount(): Int {
        return techerName.size
    }
}