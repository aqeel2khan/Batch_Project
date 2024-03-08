package com.dev.batchfinal.app_modules.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.AllWorkoutVideoListAdapter
import com.dev.batchfinal.databinding.ActivityPlayWorkoutVideoBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
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