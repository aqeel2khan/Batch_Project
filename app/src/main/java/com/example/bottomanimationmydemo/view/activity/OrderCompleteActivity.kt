package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.MainActivity
import com.example.bottomanimationmydemo.adapter.MealBatchPlanAdapter
import com.example.bottomanimationmydemo.adapter.OrderCompleteAdapter
import com.example.bottomanimationmydemo.databinding.ActivityOrderCompleteBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class OrderCompleteActivity : BaseActivity<ActivityOrderCompleteBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var name = ArrayList(
        Arrays.asList(
            "Batch Meal Plan", "Batch Meal Plan" )
    )
    private var meal_name: String? = null
    private var meal_price: String? = null
    private var meal_cal: String? = null
    private var meal_img: String? = null
    private var meal_count: String? = null

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        meal_name = intent.getStringExtra("meal_name")
        meal_price = intent.getStringExtra("meal_price")
        meal_cal = intent.getStringExtra("meal_cal")
        meal_img = intent.getStringExtra("meal_img")
        meal_count = intent.getStringExtra("meal_count")

        binding.tvWorkoutName.text = meal_name
        binding.mealPrice.text = "$"+meal_price
        binding.tvBoostKcal.text = meal_cal + " "
        binding.mealCount.text = meal_cal + " "
        buttonClicks()
        setLikeProductAdapter()
    }

    private fun setLikeProductAdapter() {
        binding.recyclerOrderComplete.apply {
            layoutManager = LinearLayoutManager(this@OrderCompleteActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = OrderCompleteAdapter(context, name, object : PositionItemClickListener<Int> {
                override fun onPositionItemSelected(item: String, postions: Int) {
//                    startActivity(Intent(this@OrderCompleteActivity, WeightLossBatchActivity::class.java))
                }
            })
        }
    }

    private fun buttonClicks() {
        binding.btnGoToHome.setOnClickListener {
            startActivity(Intent(this@OrderCompleteActivity, MainActivity::class.java))
        }
    }

    override fun getViewBinding() = ActivityOrderCompleteBinding.inflate(layoutInflater)
}