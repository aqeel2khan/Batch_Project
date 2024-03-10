package com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.app_common.BaseActivity

import com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter.IngredientsAdapter
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter.NutritionListAdapter
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter.ReviewListAdapter
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.databinding.ActivityChosenMealDetailBinding
import com.dev.batchfinal.model.chosen_meal_details_model.ChosenMealDetailsResponse
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChosenMealDetailActivity : BaseActivity<ActivityChosenMealDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()

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
        getDishDetails(dish_id!!,meal_id!!,goal_id!!);
        setupReviewListAdapter()
        startRelativeAnimation(binding.relWeightLayout)
    }


    private fun getDishDetails(dish_id: String,meal_id: String,goal_id: String) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()

            val jsonObject = JsonObject()
            jsonObject.addProperty("dish_id",dish_id)
            jsonObject.addProperty("meal_id",meal_id)
            jsonObject.addProperty("goal_id",goal_id)


            authViewModel.getDishDetailsApiCall(jsonObject)
            authViewModel.dishDetailsResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.dishDetailsResponse.removeObservers(this)
                        if (authViewModel.dishDetailsResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    showToast(response.status.toString())

//                                    sharedPreferences.saveCourseId(response.data.courseId.toString())
                                    dishNutritionListAdapter(response.data.data.nutritionDetails)
                                    val ingredientsArray: List<String> = response.data.data.ingredients.split(",")
                                    ingredientsListAdapter(ingredientsArray)

                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.dishDetailsResponse.removeObservers(this)
                        if (authViewModel.dishDetailsResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun dishNutritionListAdapter(nutritionDetailList: List<ChosenMealDetailsResponse.NutritionDetail>) {
        binding.recyclerNutritionList.apply {
            layoutManager = LinearLayoutManager(this@ChosenMealDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = NutritionListAdapter(this@ChosenMealDetailActivity,nutritionDetailList)
        }
    }

    private fun ingredientsListAdapter(ingredientsList: List<String>) {
        binding.recyclerIngredientsList.apply {
            layoutManager = LinearLayoutManager(this@ChosenMealDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = IngredientsAdapter(this@ChosenMealDetailActivity,ingredientsList)
        }
    }

    private fun setupReviewListAdapter() {
        binding.recyclerReviewList.apply {
            layoutManager = LinearLayoutManager(this@ChosenMealDetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ReviewListAdapter(this@ChosenMealDetailActivity)
        }
    }


    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
        }
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}