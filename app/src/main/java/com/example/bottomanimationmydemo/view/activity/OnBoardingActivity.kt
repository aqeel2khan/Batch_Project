package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
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

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    private val viewModel: AllViewModel by viewModels()
    lateinit var dialogBinding: CountryLangDialogBinding
    val countries = listOf(
        Country("Afghanistan", "URL_TO_FLAG_1"),
        Country("Albania", "URL_TO_FLAG_2"),
        Country("Algeria", "URL_TO_FLAG_2"),
        Country("Andorra", "URL_TO_FLAG_2"),
        Country("Angola", "URL_TO_FLAG_2"),
        Country("Antigua and Barbuda", "URL_TO_FLAG_2"),
        Country("Argentina", "URL_TO_FLAG_2"),
        Country("Armenia", "URL_TO_FLAG_2"),
        Country("Australia", "URL_TO_FLAG_2"),
        // Add more countries as needed
    )
    override fun getViewModel(): BaseViewModel {
       return viewModel
    }

    override fun initUi() {
        binding.loginButton.setOnClickListener {
//            startActivity(Intent(this@OnBoardingActivity, MainActivity::class.java))
            showCountryDailog()
        }

    }

    private fun showCountryDailog() {
        dialogBinding = CountryLangDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        setUpcountryAdapter(dialogBinding)
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
            dialogBinding.btnEnglish.setBackgroundResource(R.drawable.rectangle_button_gry_select)
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

    private fun setUpcountryAdapter(dialogBinding: CountryLangDialogBinding) {
        dialogBinding.recyclerCounty.apply {
            layoutManager = LinearLayoutManager(this@OnBoardingActivity, LinearLayoutManager.VERTICAL, false)
            adapter = CountryAdapter(countries)
        }
    }

    override fun getViewBinding() = ActivityOnBoardingBinding.inflate(layoutInflater)

}
/*

Austria
Austrian Empire*
Azerbaijan*/
