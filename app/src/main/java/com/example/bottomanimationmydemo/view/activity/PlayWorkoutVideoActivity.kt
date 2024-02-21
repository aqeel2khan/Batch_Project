package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.AllWorkoutVideoListAdapter
import com.example.bottomanimationmydemo.databinding.ActivityPlayWorkoutVideoBinding
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class PlayWorkoutVideoActivity : BaseActivity<ActivityPlayWorkoutVideoBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var courseImg = ArrayList(Arrays.asList(
        R.drawable.video_img, R.drawable.exercise_image,
        R.drawable.food, R.drawable.meal_bg, R.drawable.video_img,
        R.drawable.exercise_image)
    )

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        setAllWorkoutVideoAdapter(courseImg)
    }

    private fun setAllWorkoutVideoAdapter(courseImg: ArrayList<Int>) {
        binding.recyclerVideoWorkout.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerVideoWorkout.adapter = AllWorkoutVideoListAdapter(this, courseImg)
    }

    private fun buttonClicks() {

        binding.btnFinishWorkout.setOnClickListener {
            startActivity(Intent(this@PlayWorkoutVideoActivity, ComplateWorkoutActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityPlayWorkoutVideoBinding.inflate(layoutInflater)
}