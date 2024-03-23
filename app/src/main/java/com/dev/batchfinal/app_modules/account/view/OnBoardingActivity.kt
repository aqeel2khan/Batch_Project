package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.CountryAdapter
import com.dev.batchfinal.databinding.ActivityOnBoardingBinding
import com.dev.batchfinal.databinding.CountryLangDialogBinding
import com.dev.batchfinal.model.Country
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    private var selctedcountry: String = ""
    private val viewModel: AllViewModel by viewModels()
    private lateinit var dialogBinding: CountryLangDialogBinding
    private val countriesWithEmojis: MutableList<String> = mutableListOf()
    val list: ArrayList<Country> = ArrayList()
    var mAdapter: CountryAdapter? = null
    override fun getViewModel(): BaseViewModel {
       return viewModel
    }
    override fun initUi() {
        binding.onClickSelectCountry.setOnClickListener {
            showCountryDailog()
        }
        binding.onClickLookArround.setOnClickListener {
            startActivity(Intent(Intent(this@OnBoardingActivity, MainActivity::class.java)))
        }
        getCountries()
    }
    private var flag: String? =null
    private fun getCountries(): List<String> {
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
        for (countryCode in isoCountryCodes) {
            val locale = Locale("", countryCode)
            val countryName: String = locale.displayCountry
            val flagOffset = 0x1F1E6
            val asciiOffset = 0x41
            val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
            val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
            flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))//"AD"

            if (countryName != null) {
                val model = Country()
                model.name = countryName
                model.countryCode = countryCode
                model.flag = flag!!

                list.add(model)
                Log.d("coutry", list.toString())
            }
        }

        val resourceId = resources.getIdentifier(flag!!.toLowerCase(), "drawable", packageName)
        Log.d("dataaa", countriesWithEmojis.toString())
        return countriesWithEmojis
    }

    private fun showCountryDailog() {
        dialogBinding = CountryLangDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)

        dialog.setContentView(dialogBinding.root)
        setUpcountryAdapter(dialogBinding, list)

        dialogBinding.iconClose.setOnClickListener {

            if(list!=null && list.size>0) {
                mAdapter?.updateData(list)

                dialogBinding.editQuery.setText("")

            }
        }

        dialogBinding.editQuery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrEmpty() && s!=null){


                     mAdapter?.filter(s.toString())
//                    if(listData!=null && listData.isNotEmpty()){
//
//                        dialogBinding.recyclerCounty.adapter.notifyDataSetChanged()
//                    }
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dialogBinding.llCountry.setOnClickListener {
            dialogBinding.llCountry.background = resources.getDrawable(R.drawable.tab_sele_bg)
            dialogBinding.llLanguage.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            dialogBinding.tvCountry.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.tvLanguage.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
            dialogBinding.llCountryLayout.visibility = View.VISIBLE
            dialogBinding.llLanguageLayout.visibility = View.GONE
            dialogBinding.rlCountrySearch.visibility = View.VISIBLE
        }
        dialogBinding.llLanguage.setOnClickListener {
            dialogBinding.llCountry.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            dialogBinding.llLanguage.background = resources.getDrawable(R.drawable.tab_sele_bg)
            dialogBinding.tvCountry.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
            dialogBinding.tvLanguage.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.llCountryLayout.visibility = View.GONE
            dialogBinding.llLanguageLayout.visibility = View.VISIBLE
        }
        dialogBinding.btnEnglish.setOnClickListener {
            dialogBinding.btnEnglish.setBackgroundResource(R.drawable.rectangle_btn_lag_select)
            dialogBinding.btnArabic.setBackgroundResource(R.drawable.tab_un_sel_bg)
            sharedPreferences.saveLanguage("English")
        }
        dialogBinding.btnArabic.setOnClickListener {
            dialogBinding.btnArabic.setBackgroundResource(R.drawable.rectangle_btn_lag_select)
            dialogBinding.btnEnglish.setBackgroundResource(R.drawable.tab_un_sel_bg)
            sharedPreferences.saveLanguage("Arabic")
        }
        dialogBinding.btnCountryNext.setOnClickListener {
            dialogBinding.llCountry.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            dialogBinding.llLanguage.background = resources.getDrawable(R.drawable.tab_sele_bg)
            dialogBinding.tvCountry.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
            dialogBinding.tvLanguage.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.llCountryLayout.visibility = View.GONE
            dialogBinding.llLanguageLayout.visibility = View.VISIBLE
            dialogBinding.llLanguageLayout.visibility = View.VISIBLE
        }
        dialogBinding.btnNext.setOnClickListener {
            startActivity(Intent(this, ExploreActivity::class.java))
            finish()
        }
        dialog.show()
    }

    private fun setUpcountryAdapter(
        dialogBinding: CountryLangDialogBinding,
        list: ArrayList<Country>
    ) {

        selctedcountry = "Kuwait"
        dialogBinding.recyclerCounty.apply {
            layoutManager = LinearLayoutManager(this@OnBoardingActivity, LinearLayoutManager.VERTICAL, false)
           mAdapter=  CountryAdapter(list,this@OnBoardingActivity,selctedcountry)
            dialogBinding.recyclerCounty.adapter =mAdapter
        }
    }

    override fun getViewBinding() = ActivityOnBoardingBinding.inflate(layoutInflater)
    fun setEdittextBlank() {

        dialogBinding.editQuery.setText("")
        dialogBinding.rlCountrySearch.visibility = View.GONE
    }

}

