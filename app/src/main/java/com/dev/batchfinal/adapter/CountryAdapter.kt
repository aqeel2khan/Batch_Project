package com.dev.batchfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ItemCountryListBinding
import com.dev.batchfinal.model.Country

class CountryAdapter(private val countries: List<Country>/*, var listener: CountryListItemPosition<Int>*/) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCountryListBinding = ItemCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class ViewHolder(val binding: ItemCountryListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            // Bind data to the views
            binding.countryName.text = country.name
            Glide.with(binding.root.context).load(country.flag).into(binding.countryFlag)

            binding.root.setOnClickListener {
//                listener.on
                binding.btnCountry.setBackgroundResource(R.drawable.rectangle_btn_lag_select)
            }
        }
    }
}
