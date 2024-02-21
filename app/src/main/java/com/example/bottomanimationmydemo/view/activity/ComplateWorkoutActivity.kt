package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.bottomanimationmydemo.databinding.ActivityComplateWorkoutBinding
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
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
            startActivity(Intent(this@ComplateWorkoutActivity, SelectWorkoutTimeActivity::class.java))
        }

    }

    override fun getViewBinding() = ActivityComplateWorkoutBinding.inflate(layoutInflater)
}