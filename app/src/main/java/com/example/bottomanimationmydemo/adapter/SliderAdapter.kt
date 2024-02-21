package com.example.bottomanimationmydemo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.bottomanimationmydemo.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val images: IntArray/*, private val text: Array<String>*/) : SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.slider_item_layout, null)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        viewHolder.imageView.setImageResource(images[position])
//        viewHolder.textView.text = text[position]
    }

    override fun getCount(): Int {
        return images.size
    }

    inner class SliderViewHolder(itemView: View) :
        ViewHolder(itemView) {
        val imageView: ImageView
        val textView: TextView

        init {
            imageView = itemView.findViewById<ImageView>(R.id.image)
            textView = itemView.findViewById<TextView>(R.id.textdescription)
        }
    }
}