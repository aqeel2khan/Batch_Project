package com.example.bottomanimationmydemo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import com.example.bottomanimationmydemo.MainActivity
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ActivityExploreBinding
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExploreActivity : BaseActivity<ActivityExploreBinding>() {
    private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        binding.backButtonBack.setOnClickListener {  }
        binding.continueButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.signTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.imgBoyFrame.setOnClickListener {
//            showToast("Male")
            sharedPreferences.saveGender("Male")
            binding.imgBoyFrame.setBackgroundResource(R.drawable.rectangle_button_select)
            binding.imgGirlFrame.setBackgroundResource(R.drawable.rectangle_button_gry)
        }
        binding.imgGirlFrame.setOnClickListener {
//            showToast("FmMale")
            sharedPreferences!!.saveGender("Female")
            binding.imgBoyFrame.setBackgroundResource(R.drawable.rectangle_button_gry)
            binding.imgGirlFrame.setBackgroundResource(R.drawable.rectangle_button_select)
        }
    }

    override fun getViewBinding() = ActivityExploreBinding.inflate(layoutInflater)

}