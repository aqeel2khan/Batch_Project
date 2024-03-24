package com.dev.batchfinal.adapter

import android.annotation.SuppressLint
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
    private var mContext: OnBoardingActivity,
    private var mselctedcountry: String,
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {
    private var filteredList: List<Country> = countries.toMutableList()
    private  var mcContext = this.mContext
    private var selctedcountry = mselctedcountry


    fun updateData(newData: List<Country>) {
        countries = newData
        notifyDataSetChanged()
    }
    fun updateCurrentCountry(countryName:String){
        selctedcountry =  countryName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCountryListBinding = ItemCountryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding,mContext)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(countries[position],selctedcountry)
    }

    override fun getItemCount(): Int {
        return countries.size
    }


    @SuppressLint("SuspiciousIndentation")
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

        fun bind(country: Country, selctedcountry: String) {
            // Bind data to the views
            binding.countryName.text = country.name
            if(country.name.equals(selctedcountry)){
                binding.btnCountry.setBackgroundResource(R.drawable.rectangle_btn_lag_select)
            }else{
                binding.btnCountry.setBackgroundResource(R.drawable.rectangle_button_gry)
            }
            Glide.with(binding.root.context).load(country.flag).into(binding.countryFlag)

            binding.root.setOnClickListener {
//                listener.on
                binding.btnCountry.setBackgroundResource(R.drawable.rectangle_btn_lag_select)

                mContext.setEdittextBlank()
            }
        }


    }
}
