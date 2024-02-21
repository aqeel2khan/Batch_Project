package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.bottomanimationmydemo.databinding.ActivityWorkOutDetailScreenBinding
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WorkOutDetailScreen : BaseActivity<ActivityWorkOutDetailScreenBinding>() {
   private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        startRelativeAnimation(binding.relWeightDetailLayout)
    }

    private fun buttonClicks() {
        binding.btnStartWorkout.setOnClickListener {
            startActivity(Intent(this@WorkOutDetailScreen, SlideWorkoutVideoActivity::class.java)) }
//            startActivity(Intent(this@WorkOutDetailScreen, PlayWorkoutVideoActivity::class.java)) }
    }

    override fun getViewBinding() = ActivityWorkOutDetailScreenBinding.inflate(layoutInflater)
}