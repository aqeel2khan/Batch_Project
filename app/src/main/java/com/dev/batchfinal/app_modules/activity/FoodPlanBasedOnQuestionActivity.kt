package com.dev.batchfinal.app_modules.activity

import android.content.Intent
import androidx.activity.viewModels
import com.dev.batchfinal.databinding.ActivityFoodPlanBasedOnQuestionBinding
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity.MealDetailsActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FoodPlanBasedOnQuestionActivity : BaseActivity<ActivityFoodPlanBasedOnQuestionBinding>() {
    private val viewModel: AllViewModel by viewModels()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
//        setupAnim()

    }

    private fun buttonClicks() {
        binding.youMealBatch.setOnClickListener {
            startActivity(Intent(this@FoodPlanBasedOnQuestionActivity, MealDetailsActivity::class.java))
        }
        binding.btnBackToMeal.setOnClickListener {
            //redirect to meal fragment
        }
    }
 /*   private fun setupAnim() {
        binding.animationView.setAnimation(R.raw.progressbar)
        binding.animationView.repeatCount = LottieDrawable.RESTART
        binding.animationView.playAnimation()
    }*/

    override fun getViewBinding() = ActivityFoodPlanBasedOnQuestionBinding.inflate(layoutInflater)
}