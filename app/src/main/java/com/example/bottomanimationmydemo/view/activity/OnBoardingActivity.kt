package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.CountryAdapter
import com.example.bottomanimationmydemo.databinding.ActivityOnBoardingBinding
import com.example.bottomanimationmydemo.databinding.CountryLangDialogBinding
import com.example.bottomanimationmydemo.model.Country
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    private val viewModel: AllViewModel by viewModels()
    lateinit var dialogBinding: CountryLangDialogBinding
    val countriesWithEmojis: MutableList<String> = mutableListOf()
    val list: ArrayList<Country> = ArrayList()
    override fun getViewModel(): BaseViewModel {
       return viewModel
    }

    override fun initUi() {
        binding.loginButton.setOnClickListener {
//            startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            showCountryDailog()
        }
        getCountries()
    }

    private var flag: String? =null
    fun getCountries(): List<String> {
        val isoCountryCodes: Array<String> = Locale.getISOCountries()
//        val countriesWithEmojis: MutableList<String> = mutableListOf()
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
//            countriesWithEmojis.add("$countryName $flag")
        }

        val resourceId = resources.getIdentifier(flag!!.toLowerCase(), "drawable", packageName)

        // Set the flag image to the ImageView

        // Set the flag image to the ImageView
//        flagImageView.setImageResource(resourceId)
        Log.d("dataaa", countriesWithEmojis.toString())
        return countriesWithEmojis
    }

    private fun showCountryDailog() {
        dialogBinding = CountryLangDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        setUpcountryAdapter(dialogBinding, list)
        dialogBinding.llCountry.setOnClickListener {
            dialogBinding.llCountry.background = resources.getDrawable(R.drawable.tab_sele_bg)
            dialogBinding.llLanguage.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            dialogBinding.tvCountry.setTextColor(ContextCompat.getColor(this,R.color.white))
            dialogBinding.tvLanguage.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
            dialogBinding.llCountryLayout.visibility = View.VISIBLE
            dialogBinding.llLanguageLayout.visibility = View.GONE
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
        }
        dialog.show()
    }

    private fun setUpcountryAdapter(
        dialogBinding: CountryLangDialogBinding,
        list: ArrayList<Country>
    ) {
        dialogBinding.recyclerCounty.apply {
            layoutManager = LinearLayoutManager(this@OnBoardingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CountryAdapter(list)
        }
    }

    override fun getViewBinding() = ActivityOnBoardingBinding.inflate(layoutInflater)

}
/*

Austria
Austrian Empire*
Azerbaijan*/
