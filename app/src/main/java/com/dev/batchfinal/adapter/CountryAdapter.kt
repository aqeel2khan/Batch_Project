package com.dev.batchfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.batchfinal.R
import com.dev.batchfinal.app_modules.account.view.OnBoardingActivity
import com.dev.batchfinal.databinding.ItemCountryListBinding
import com.dev.batchfinal.model.Country

class CountryAdapter(
    private var countries: List<Country>, /*, var listener: CountryListItemPosition<Int>*/
    private  var mContext: OnBoardingActivity,
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private var filteredList: List<Country> = countries.toMutableList()
    private  var mcContext = this.mContext


    fun updateData(newData: List<Country>) {
        countries = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCountryListBinding = ItemCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,mContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount(): Int {
        return countries.size
    }


    fun filter(query: String) {
        filteredList = if (query.isEmpty()) {
            countries
        }  else {
         countries.filter { it.name.contains(query, ignoreCase = true) }
        }


        if(filteredList!=null && filteredList.isNotEmpty())
        updateData(filteredList)
//        return filteredList

//        this.notifyDataSetChanged()

    }

    class ViewHolder(val binding: ItemCountryListBinding,val mContext: OnBoardingActivity) : RecyclerView.ViewHolder(binding.root) {
        fun bind(country: Country) {
            // Bind data to the views
            binding.countryName.text = country.name
            Glide.with(binding.root.context).load(country.flag).into(binding.countryFlag)

            binding.root.setOnClickListener {
//                listener.on
                binding.btnCountry.setBackgroundResource(R.drawable.rectangle_btn_lag_select)

                mContext.setEdittextBlank()
            }
        }
    }
}
