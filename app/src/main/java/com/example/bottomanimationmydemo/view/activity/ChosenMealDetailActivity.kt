package com.example.bottomanimationmydemo.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.ReviewListAdapter
import com.example.bottomanimationmydemo.databinding.ActivityChosenMealDetailBinding
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChosenMealDetailActivity : BaseActivity<ActivityChosenMealDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private var dish_id: String? = null
    private var meal_id: String? = null
    private var goal_id: String? = null

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        dish_id = intent.getStringExtra("dish_id")
        meal_id = intent.getStringExtra("meal_id")
        goal_id = intent.getStringExtra("goal_id")

        setupReviewListAdapter()
        startRelativeAnimation(binding.relWeightLayout)
    }

    private fun setupReviewListAdapter() {
        binding.recyclerReviewList.apply {
            layoutManager = LinearLayoutManager(this@ChosenMealDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewListAdapter(this@ChosenMealDetailActivity)
        }
    }


    private fun buttonClicks() {
        binding.txtRateMeal.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_rate_meal = dialog.findViewById<LinearLayout>(R.id.ll_rate_meal)
        val btn_submit = dialog.findViewById<Button>(R.id.btn_submit)
        val write_review = dialog.findViewById<EditText>(R.id.write_review)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_rate_meal!!.visibility = View.VISIBLE
        btn_submit!!.setOnClickListener {
            //code for save week price
            showReviewSubmitDialog()
            dialog.dismiss()
        }
        write_review!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(s.toString().trim().isEmpty()){
                    btn_submit.setBackgroundResource(R.drawable.rectangle_button_gry)
                } else {
                    btn_submit.setBackgroundResource(R.drawable.rectangle_button)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        dialog.show()
    }

    private fun showReviewSubmitDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_review_submit = dialog.findViewById<LinearLayout>(R.id.ll_review_submit)
        val btn_ok = dialog.findViewById<Button>(R.id.btn_ok)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_review_submit!!.visibility = View.VISIBLE
        btn_ok!!.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }


        dialog.show()
    }

    override fun getViewBinding() = ActivityChosenMealDetailBinding.inflate(layoutInflater)

    companion object {
        fun getBundle(s: String): Bundle? {
            val bundle = Bundle()
            return bundle
        }
    }

}