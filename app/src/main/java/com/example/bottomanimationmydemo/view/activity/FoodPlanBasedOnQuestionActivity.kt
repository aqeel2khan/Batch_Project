package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.airbnb.lottie.LottieDrawable
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ActivityFoodPlanBasedOnQuestionBinding
import com.example.bottomanimationmydemo.meals.meal_unpurchase.view.activity.MealDetailsActivity
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
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