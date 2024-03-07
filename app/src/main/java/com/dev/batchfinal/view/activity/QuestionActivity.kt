package com.dev.batchfinal.view.activity

import android.content.Intent
import android.graphics.Color
import android.view.View
import androidx.activity.viewModels
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityQuestionBinding
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionActivity : BaseActivity<ActivityQuestionBinding>() {
   private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel




    }

    override fun initUi() {
        buttonClicks()
    }

    private fun buttonClicks() {
        binding.btnNext1.setOnClickListener {
            binding.image2.setImageResource(R.drawable.sel_circle)
            binding.rlLine2.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.fitnessGoal.visibility = View.GONE
            binding.llYourAge.visibility = View.VISIBLE
        }
        binding.btnNext2.setOnClickListener {
            binding.image3.setImageResource(R.drawable.sel_circle)
            binding.rlLine3.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.llYourAge.visibility = View.GONE
            binding.llHeight.visibility = View.VISIBLE
        }
        binding.btnNext3.setOnClickListener {
            binding.image4.setImageResource(R.drawable.sel_circle)
            binding.rlLine4.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.llHeight.visibility = View.GONE
            binding.llWeight.visibility = View.VISIBLE
        }
        binding.btnNext4.setOnClickListener {
            binding.image5.setImageResource(R.drawable.sel_circle)
            binding.rlLine5.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.llWeight.visibility = View.GONE
            binding.activeYouAre.visibility = View.VISIBLE
        }
        binding.btnNext5.setOnClickListener {
            binding.image6.setImageResource(R.drawable.sel_circle)
            binding.rlLine6.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.activeYouAre.visibility = View.GONE
            binding.chooseYourDiet.visibility = View.VISIBLE
        }
        binding.btnNext6.setOnClickListener {
            binding.image7.setImageResource(R.drawable.sel_circle)
//            binding.rlLine7.setBackgroundColor(Color.parseColor("#CDA87F"))
            binding.chooseYourDiet.visibility = View.GONE
            binding.txtAllergic.visibility = View.VISIBLE
        }
        binding.btnNext7.setOnClickListener {
            startActivity(Intent(this@QuestionActivity, FoodPlanBasedOnQuestionActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityQuestionBinding.inflate(layoutInflater)
}