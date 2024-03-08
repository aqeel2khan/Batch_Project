package com.dev.batchfinal.app_modules.account.view

import android.content.Intent
import android.widget.Toast
import androidx.activity.viewModels
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityExploreBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
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
            if (getSetting("GENDER","")!!.isNotEmpty())
            {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            }else{
                Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show()
            }

        }
        binding.signTextView.setOnClickListener {
            if (getSetting("GENDER","")!!.isNotEmpty())
            {
                startActivity(Intent(this, LoginActivity::class.java))

            }else{
                Toast.makeText(this,"Please select gender",Toast.LENGTH_SHORT).show()
            }
        }
        binding.imgBoyFrame.setOnClickListener {
            setSetting("GENDER","Male")
            binding.imgBoyFrame.setBackgroundResource(R.drawable.rectangle_button_select)
            binding.imgGirlFrame.setBackgroundResource(R.drawable.rectangle_button_gry)
        }
        binding.imgGirlFrame.setOnClickListener {
            setSetting("GENDER","Female")
            binding.imgBoyFrame.setBackgroundResource(R.drawable.rectangle_button_gry)
            binding.imgGirlFrame.setBackgroundResource(R.drawable.rectangle_button_select)
        }
    }

    override fun getViewBinding() = ActivityExploreBinding.inflate(layoutInflater)

}