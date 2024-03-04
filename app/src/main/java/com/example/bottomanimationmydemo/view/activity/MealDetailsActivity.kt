package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.AllTypeOfMealAdapter
import com.example.bottomanimationmydemo.adapter.MealPlanListAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityMealDetailsBinding
import com.example.bottomanimationmydemo.`interface`.CategoryListItemPosition
import com.example.bottomanimationmydemo.`interface`.MealDishListItemPosition
import com.example.bottomanimationmydemo.model.meal_detail_model.Category
import com.example.bottomanimationmydemo.model.meal_detail_model.MealDetails
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class MealDetailsActivity : BaseActivity<ActivityMealDetailsBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var meal_id: String? = null
    private var gole_id: String? = null
    private var meal_name: String? = null
    private var meal_price: String? = null
    private var meal_cal: String? = null
    private var meal_img: String? = null
    private var meal_count: String? = null
    private var meal_snack: String? = null
    var selectedValuesString = ""
    private var mealDishData:ArrayList<MealDishData> = ArrayList()
    val dataList: ArrayList<String> = ArrayList()
    val weekString = " Weeks "
    var cat_id: Int = 0

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        meal_id = intent.getStringExtra("id")
        buttonClicks()
        getMealDetails()
//        setupMealPlanList(mealData.categoryList as List<Category>)
//        setUpAllMealsAdapter()
    }

    private fun getMealDetails() {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealDetailApiCall(meal_id!!)
            authViewModel.mealDetailResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealDetailResponse.removeObservers(this)
                        if (authViewModel.mealDetailResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
//                                    sharedPreferences.saveCourseId(response.data.courseId.toString())
                                    setUpMealDetails(response.data.data)
                                    gole_id=response.data.data.goal_id
                                    meal_name=response.data.data.name
                                    meal_price=response.data.data.price
                                    meal_cal=response.data.data.avgCalPerDay
                                    meal_img=response.data.data.meal_image
                                    meal_count=response.data.data.mealCount.toString()
                                    meal_snack=response.data.data.snack_count.toString()                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealDetailResponse.removeObservers(this)
                        if (authViewModel.mealDetailResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setUpMealDetails(mealData: MealDetails) {
        if(mealData != null){
            binding.tvMealName.text = mealData.name
            binding.tvMealPrice.text = mealData.price + "KWD"
            binding.tvMealDescription.text = mealData.description
            binding.tvMealKcl.text = mealData.avgCalPerDay + " kcl"
            binding.tvMealCount.text = mealData.mealCount.toString() + " meals"
            binding.mealType.text = /*"Batch "+*/mealData.mealType
            binding.selectedItem.text = mealData.price + "KWD"
            val cat_list = mealData.categoryList
            for (i in cat_list){
                cat_id = i.categoryId
                Log.d("category", cat_list.toString())
            }
            getMealDish(1)
            if(!mealData.categoryList.isNullOrEmpty() ){
                setupMealPlanList(mealData.categoryList as List<Category>)
            }
            setUpAllMealsAdapter(mealDishData)

            val commaSeparatedValues = mealData.duration
            val valuesArray = commaSeparatedValues.split(",").toTypedArray()

            val arrayList = ArrayList<String>()
            arrayList.addAll(valuesArray)
            for (item in arrayList) {
                if (item.equals("Select Duration")){
                    val newItem = item
                    dataList.add(newItem)
                }else{
                    val newItem = item + weekString
                    dataList.add(newItem)
                }
            }
            setSpinnerDuration(dataList)
        }else{}
    }

    private fun setSpinnerDuration(dataList: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selectedValue = dataList[position]
                // Append the selected value to the string variable
                selectedValuesString += if (selectedValuesString.isEmpty()) selectedValue else ", $selectedValue"
                showToast(selectedValuesString)
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }

    private fun setupMealPlanList(categories: List<Category>) {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(this@MealDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = MealPlanListAdapter(this@MealDetailsActivity, categories, object :
                CategoryListItemPosition<Int> {
                override fun onCategoryListItemPosition(item: Category, position: Int) {
                    //dish items
//                    setUpAllMealsAdapter()
                    getMealDish(item.categoryId)
                }
            })
        }
    }

    private fun getMealDish(categoryId: Int) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealDishApiCall(categoryId)
            authViewModel.mealDishResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealDishResponse.removeObservers(this)
                        if (authViewModel.mealDishResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    mealDishData = response.data.data
                                    setUpAllMealsAdapter(mealDishData)
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealDishResponse.removeObservers(this)
                        if (authViewModel.mealDishResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setUpAllMealsAdapter(mealdishList: List<MealDishData>) {
        binding.recyclerAllMeal.apply {
            layoutManager = GridLayoutManager(this@MealDetailsActivity, 2)
            adapter = AllTypeOfMealAdapter(this@MealDetailsActivity, mealdishList,
                object : MealDishListItemPosition<Int> {
                    override fun onMealDishListItemPosition(item: MealDishData, position: Int) {
                     //redirect code here
                        val intent = Intent(this@MealDetailsActivity, ChosenMealDetailActivity::class.java)
                        intent.putExtra("dish_id",item.dishId.toString())
                        intent.putExtra("meal_id",item.mealId.toString())
                        intent.putExtra("goal_id",gole_id)
                        startActivity(intent)
                    }
                })
        }
    }

    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
           onBackPressed()
        }
        binding.txtAddPromo.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.btnSubscribePlan.setOnClickListener {
            if (sharedPreferences.token != "") {
                startActivity(
                    Intent(this@MealDetailsActivity, CheckOutActivity::class.java)
                        .putExtra("meal_id", meal_id)
                        .putExtra("screen", "BatchMeal")
                        .putExtra("meal_name", meal_name)
                        .putExtra("meal_price", meal_price)
                        .putExtra("meal_cal", meal_cal)
                        .putExtra("meal_img", meal_img)
                        .putExtra("meal_count", meal_count)
                        .putExtra("meal_snack", meal_snack)
                )
            } else {
                startActivity(
                    Intent(this@MealDetailsActivity, LoginActivity::class.java)
                )
            }
//            startActivity(Intent(this@MealDetailsActivity, RegistrationActivity::class.java).putExtra("batchMeal", "BatchMeal"))
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_add_promo = dialog.findViewById<LinearLayout>(R.id.ll_add_promo)
        val btn_add = dialog.findViewById<Button>(R.id.btn_add)
        val add_promo_code = dialog.findViewById<EditText>(R.id.add_promo_code)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_add_promo!!.visibility = View.VISIBLE
        btn_add!!.setOnClickListener {
            //code for save week price
            showReviewSubmitDialog()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showReviewSubmitDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_review_submit = dialog.findViewById<LinearLayout>(R.id.ll_review_submit)
        val btn_ok = dialog.findViewById<Button>(R.id.btn_ok)
        val success_msg = dialog.findViewById<TextView>(R.id.success_msg)
        val content = dialog.findViewById<TextView>(R.id.content)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_review_submit!!.visibility = View.VISIBLE
        content!!.visibility = View.GONE
        success_msg!!.text = "Promo-code applied \nsuccessfully!"
        btn_ok!!.setOnClickListener {
            //code for save week price
            binding.txtAddPromo.text = resources.getString(R.string.txt_remove_pro)
            dialog.dismiss()
        }


        dialog.show()
    }

    override fun getViewBinding() = ActivityMealDetailsBinding.inflate(layoutInflater)

    companion object {
        fun getBundle(s: String): Bundle? {
            val bundle = Bundle()
            return bundle
        }
    }

    override fun onResume() {
//        handleHeader(false)
//        handleBottomBar(false)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}