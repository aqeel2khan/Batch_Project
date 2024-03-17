package com.dev.batchfinal.app_modules.question.view

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_custom.CommaSeprater
import com.dev.batchfinal.app_modules.question.adapter.HowActiveYouAdapter
import com.dev.batchfinal.app_modules.question.adapter.MealAllergiesListAdapter
import com.dev.batchfinal.app_modules.question.adapter.MealGoalsListAdapter
import com.dev.batchfinal.app_modules.question.adapter.MealTagsListAdapter
import com.dev.batchfinal.app_modules.question.model.HowActiveYouModel
import com.dev.batchfinal.app_modules.question.model.meal_allergies.MealAllergies
import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoals
import com.dev.batchfinal.app_modules.question.model.meal_tags.MealTags
import com.dev.batchfinal.app_utils.*
import com.dev.batchfinal.app_utils.MyConstant.jsonObject
import com.dev.batchfinal.databinding.ActivityQuestionBinding
import com.dev.batchfinal.`interface`.HowActiveAreListItemPosition
import com.dev.batchfinal.`interface`.MealAllergiesListItemPosition
import com.dev.batchfinal.`interface`.MealGoalsListItemPosition
import com.dev.batchfinal.`interface`.MealTagsListItemPosition
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar

@AndroidEntryPoint
class QuestionActivity : BaseActivity<ActivityQuestionBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var meal_goal_id: Int = 0
    private var selected_date: String? = null
    private var ageString: String? = null
    private var commaSepratedString: String? = null
    private var meal_tag_id: Int = 0
    private var howActiveYou_id: Int = 0
    private var selected_allergies_id: Int = 0
    private var selectedHeight: Float = 0.0F
    private var selectedCurrentWeight: Float = 0.0F
    private var selectedTargetWeight: Float = 0.0F
    val currentSelectedItems: ArrayList<String> = ArrayList()

    //    var howActiveYouList = ArrayList( Arrays.asList("Low Mobility", "12 workouts per week", "35 workouts per week", "57 workouts per week" ))
    var howActiveList = ArrayList<HowActiveYouModel>()
    override fun initUi() {
        buttonClicks()
        getMealGoalsApi()
        howActiveList = arrayListOf<HowActiveYouModel>(
            HowActiveYouModel(id = 0, name = "Low Mobility", ""),
            HowActiveYouModel(id = 1, name = "1-2 workouts per week", ""),
            HowActiveYouModel(id = 2, name = "3-5 workouts per week", ""),
            HowActiveYouModel(id = 4, name = "5-7 workouts per week", ""),
        )
    }

    private fun getMealGoalsApi() {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealGoalsApiCall()
            authViewModel.mealGoalsResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealGoalsResponse.removeObservers(this)
                        if (authViewModel.mealGoalsResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.success) {
                                        setMealGoalsAdapter(response.data.data)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealGoalsResponse.removeObservers(this)
                        if (authViewModel.mealGoalsResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    private fun setMealGoalsAdapter(mealGoalsList: List<MealGoals>) {
        binding.recyclerMealGoals.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MealGoalsListAdapter(this@QuestionActivity, mealGoalsList, object :
                MealGoalsListItemPosition<Int> {
                override fun onMealGoalsListItemPosition(item: List<MealGoals>, position: Int) {
                    meal_goal_id = item[position].id
                }
            })
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun buttonClicks() {
        binding.btnNext1.setOnClickListener {
            if (meal_goal_id == 0){
                showToast("Please select goal")
            }else{
                binding.image2.setImageResource(R.drawable.sel_circle)
                binding.rlLine2.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.fitnessGoal.visibility = View.GONE
                binding.llYourAge.visibility = View.VISIBLE
                val today = Calendar.getInstance()
                binding.datePicker1.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH)) { view, year, month, day ->
                    val month = month + 1
                    selected_date = "$day/$month/$year"
                    calculateAge(year, month, day)
//                val msg = "You Selected: $day/$month/$year"
                }
            }
        }
        binding.btnNext2.setOnClickListener {
            if (ageString.isNullOrEmpty()){
                showToast("Please select date of birth")
            }else{
                binding.image3.setImageResource(R.drawable.sel_circle)
                binding.rlLine3.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.llYourAge.visibility = View.GONE
                binding.llHeight.visibility = View.VISIBLE
                binding.heightRuler.visibility = View.VISIBLE
                binding.heightFeetRuler.visibility = View.GONE
                binding.heightRuler.setOnValueChangeListener(object : RulerView.OnValueChangeListener {
                    override fun onChange(view: RulerView?, value: Float) {
                        selectedHeight = value
//                        showToast("Your height is $value meters")
                    }
                })
                binding.llCm.setOnClickListener {
                    binding.llCm.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.llFeet.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.tvCm.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
                    binding.tvFeet.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
//                    binding.heightRuler.setMinValue(50F)
//                    binding.heightRuler.setDefaultValue(150F)
//                    binding.heightRuler.setMaxValue(300F)
                    binding.heightRuler.visibility = View.VISIBLE
                    binding.heightFeetRuler.visibility = View.GONE
                    binding.heightRuler.setOnValueChangeListener(object : RulerView.OnValueChangeListener {
                        override fun onChange(view: RulerView?, value: Float) {
                            selectedHeight = value
//                        showToast("Your height is $value meters")
                        }
                    })
                }
                binding.llFeet.setOnClickListener {
                    binding.llCm.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.llFeet.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.tvCm.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
                    binding.tvFeet.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
//                    binding.heightRuler.setDefaultValue(5.4f)
//                    binding.heightRuler.setMinValue(1.64f)
//                    binding.heightRuler.setMaxValue(9.84f)
                    binding.heightRuler.visibility = View.GONE
                    binding.heightFeetRuler.visibility = View.VISIBLE
                    binding.heightFeetRuler.setOnValueChangeListener(object : RulerView.OnValueChangeListener {
                        override fun onChange(view: RulerView?, value: Float) {
                            selectedHeight = value
//                        showToast("Your height is $value meters")
                        }
                    })
                }
            }
        }
        binding.btnNext3.setOnClickListener {
            if (selectedHeight.equals(0.0)){
                showToast("Please select height")
            }else{
                binding.image4.setImageResource(R.drawable.sel_circle)
                binding.rlLine4.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.llHeight.visibility = View.GONE
                binding.llWeight.visibility = View.VISIBLE
                //current weight
                binding.ruler2.initViewParam(50F, 0F, 250F, 0.1f)
                binding.ruler2.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                    override fun onChooseValueChange(value: Float) {
                        // TODO do some work
                        selectedCurrentWeight = value
                    }
                })
                binding.llKg.setOnClickListener {
                    binding.llKg.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.llLbs.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.tvKg.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
                    binding.tvLbs.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
                    //kg value data
                    binding.ruler2.initViewParam(50F, 0F, 250F, 0.1f)
                    binding.ruler2.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                        override fun onChooseValueChange(value: Float) {
                            // TODO do some work
                            selectedCurrentWeight = value
                        }
                    })
                }
                binding.llLbs.setOnClickListener {
                    binding.llKg.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.llLbs.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.tvKg.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
                    binding.tvLbs.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
                    //lbs value data
                    binding.ruler2.initViewParam(150.0F, 0.0F, 551.16f, 0.1f)
                    binding.ruler2.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                        override fun onChooseValueChange(value: Float) {
                            // TODO do some work
                            selectedCurrentWeight = value
                        }
                    })
                }
                //target weight
                binding.targetRuler.initViewParam(50.0F, 0.0F, 551.16f, 0.1f)
                binding.targetRuler.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                    override fun onChooseValueChange(value: Float) {
                        // TODO do some work
                        selectedTargetWeight = value
                    }
                })
                binding.llTrKg.setOnClickListener {
                    binding.llTrKg.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.llTrLbs.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.tvTrKg.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
                    binding.tvTrLbs.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
                    binding.targetRuler.initViewParam(50.0F, 0.0F, 551.16f, 0.1f)
                    binding.targetRuler.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                        override fun onChooseValueChange(value: Float) {
                            // TODO do some work
                            selectedTargetWeight = value
                        }
                    })
                }
                binding.llTrLbs.setOnClickListener {
                    binding.llTrKg.background = resources.getDrawable(R.drawable.cmfeet_bg)
                    binding.llTrLbs.background = resources.getDrawable(R.drawable.demo_wt)
                    binding.tvTrKg.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt_gry))
                    binding.tvTrLbs.setTextColor(ContextCompat.getColor(this,R.color.welcome_txt))
                    binding.targetRuler.initViewParam(150.0F, 0.0F, 551.16f, 0.1f)
                    binding.targetRuler.setChooseValueChangeListener(object : com.dev.batchfinal.app_custom.RulerView.OnChooseResulterListener {
                        override fun onChooseValueChange(value: Float) {
                            // TODO do some work
                            selectedTargetWeight = value
                        }
                    })
                }
            }

        }
        binding.btnNext4.setOnClickListener {
            if (selectedCurrentWeight.equals(0.0) && selectedTargetWeight.equals(0.0)){
                showToast("Please select weight")
            }else{
                binding.image5.setImageResource(R.drawable.sel_circle)
                binding.rlLine5.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.llWeight.visibility = View.GONE
                binding.activeYouAre.visibility = View.VISIBLE
                setHowActiveYouList(howActiveList)
            }

        }
        binding.btnNext5.setOnClickListener {
            if (howActiveYou_id == 0){
                showToast("Please select workout per week")
            }else{
                binding.image6.setImageResource(R.drawable.sel_circle)
                binding.rlLine6.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.activeYouAre.visibility = View.GONE
                binding.chooseYourDiet.visibility = View.VISIBLE
                getMealTagsList()
            }

        }
        binding.btnNext6.setOnClickListener {
            if (meal_tag_id == 0){
                showToast("Please select meal tag")
            }else{
                binding.image7.setImageResource(R.drawable.sel_circle)
//            binding.rlLine7.setBackgroundColor(Color.parseColor("#CDA87F"))
                binding.chooseYourDiet.visibility = View.GONE
                binding.txtAllergic.visibility = View.VISIBLE
                getMealAllergiesApi()
            }

        }
        binding.btnNext7.setOnClickListener {
            jsonObject.addProperty("goal_id",meal_goal_id)
            jsonObject.addProperty("age",ageString)
            jsonObject.addProperty("height",selectedHeight.toInt())
            jsonObject.addProperty("current_weight",selectedCurrentWeight.toInt())
            jsonObject.addProperty("target_weight",selectedTargetWeight.toInt())
            jsonObject.addProperty("workout_per_week",howActiveYou_id)
            jsonObject.addProperty("tag_id",commaSepratedString)
            jsonObject.addProperty("allergic_id",selected_allergies_id)
            submitQuestionsApi(jsonObject)
//
        }
    }

    private fun submitQuestionsApi(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()

            authViewModel.submitAllQuestionApiCall(jsonObject)
            authViewModel.submitAllQuestionResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.submitAllQuestionResponse.removeObservers(this)
                       if (authViewModel.submitAllQuestionResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.success) {
//                                    showToast("success")
                                        showToast(response.data.internal.avgCalPerDay)
                                    startActivity(Intent(this@QuestionActivity, ProcessingQuestionActivity::class.java).putExtra("avgCalPerDay", response.data.internal.avgCalPerDay))
//                                    startActivity(Intent(this@QuestionActivity, FoodPlanBasedOnQuestionActivity::class.java))
//
//                                        setMealAllergiesAdapter(response.data.data)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.submitAllQuestionResponse.removeObservers(this)
                        if (authViewModel.submitAllQuestionResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun calculateAge(year: Int, month: Int, day: Int) {
        val dob = Calendar.getInstance()
        dob.set(year, month, day)
        val today = Calendar.getInstance()

        var age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        ageString = age.toString()
        /*ageString = if (age < 0) {
            "Invalid Date"
        } else {
            "Age: $age years"
        }*/

        showToast(ageString)
    }

    private fun getMealAllergiesApi() {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealAllergiesApiCall()
            authViewModel.mealAllergiesResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealAllergiesResponse.removeObservers(this)
                        if (authViewModel.mealAllergiesResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.success) {
                                        setMealAllergiesAdapter(response.data.data)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealAllergiesResponse.removeObservers(this)
                        if (authViewModel.mealAllergiesResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setMealAllergiesAdapter(allergiesList: List<MealAllergies>) {
        binding.recyclerMealAllergies.apply {
            layoutManager = GridLayoutManager(this@QuestionActivity, 3)
            adapter = MealAllergiesListAdapter(
                this@QuestionActivity,
                allergiesList,
                object : MealAllergiesListItemPosition<Int> {
                    override fun onMealAllergiesListItemPosition(item: List<MealAllergies>, position: Int) {
                        //clickd
                        selected_allergies_id = item[position].id
                    }

                })
        }
    }

    private fun getMealTagsList() {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealTagsApiCall()
            authViewModel.mealTagsResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealTagsResponse.removeObservers(this)
                        if (authViewModel.mealTagsResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.success) {
                                        setMealTagsAdapter(response.data.data)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealTagsResponse.removeObservers(this)
                        if (authViewModel.mealTagsResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setMealTagsAdapter(mealTagsList: List<MealTags>) {
        binding.chooseDiet.apply {
            layoutManager =
                LinearLayoutManager(this@QuestionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = MealTagsListAdapter(this@QuestionActivity, mealTagsList, object :
                MealTagsListItemPosition<Int> {
                override fun onMealTagsListItemPosition(item: List<MealTags>, position: Int) {
                    meal_tag_id = item[position].id
                    currentSelectedItems!!.add(item[position].id.toString())
                    commaSepratedString = CommaSeprater.commaSeparatedString(currentSelectedItems)
                    Log.d("commaSepratedString", commaSepratedString!!)
                }
            })
        }
    }

    private fun setHowActiveYouList(activeList: ArrayList<HowActiveYouModel>) {
        binding.recyclerHowAreActive.apply {
            layoutManager = LinearLayoutManager(this@QuestionActivity, LinearLayoutManager.VERTICAL, false)
            adapter = HowActiveYouAdapter(this@QuestionActivity, activeList, object :
                HowActiveAreListItemPosition<Int> {
                override fun onHowActiveAreListItemPosition(
                    item: HowActiveYouModel,
                    position: Int
                ) {
                    howActiveYou_id = item.id
                }
            })
        }
    }

    override fun getViewBinding() = ActivityQuestionBinding.inflate(layoutInflater)
}

/* final question
* http://admin.batch.com.co/public/api/v1/meal/questions?goal_id=1&age=25&height=127&current_weight=50&target_weight=60&workout_per_week=2&tag_id=1,2,3&allergic_id=1*/