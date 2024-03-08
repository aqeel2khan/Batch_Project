package com.dev.batchfinal.app_modules.activity

import android.content.Intent
import androidx.activity.viewModels
import com.dev.batchfinal.databinding.ActivityComplateWorkoutBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComplateWorkoutActivity : BaseActivity<ActivityComplateWorkoutBinding>() {
    private val viewModel: AllViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.btnContinue.setOnClickListener {
            startActivity(
                Intent(
                    this@ComplateWorkoutActivity,
                    SelectWorkoutTimeActivity::class.java
                )
            )
        }

    }

    override fun getViewBinding() = ActivityComplateWorkoutBinding.inflate(layoutInflater)
}