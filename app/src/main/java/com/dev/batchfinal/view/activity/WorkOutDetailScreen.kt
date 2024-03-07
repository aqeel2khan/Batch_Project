package com.dev.batchfinal.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dev.batchfinal.utils.MyCustom
import com.dev.batchfinal.utils.showToast
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityWorkOutDetailScreenBinding
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.utils.CheckNetworkConnection
import com.dev.batchfinal.utils.MyConstant
import com.dev.batchfinal.utils.MyUtils
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class WorkOutDetailScreen : BaseActivity<ActivityWorkOutDetailScreenBinding>() {
    private lateinit var workout_duration_detail: Course_duration
    private lateinit var courseData: OrderList
    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        val gson = Gson()
        val strObj = intent.getStringExtra("duration_work_position")
        workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)

        val strObj1 = intent.getStringExtra("course_data")
        courseData = gson.fromJson(strObj1, OrderList::class.java)

        workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
        binding.weightLossText.text = workout_duration_detail.day_name
        binding.txtDetailContent.text = workout_duration_detail.description
        binding.userName.text = courseData.course_detail.coach_detail.name.toString()
        MyUtils.loadImage(
            binding.profileImage,
            MyConstant.IMAGE_BASE_URL + courseData.course_detail.coach_detail.profile_photo_path
        )
        buttonClicks()
        startRelativeAnimation(binding.relWeightDetailLayout)
    }

    private fun buttonClicks() {

        MyConstant.jsonObject.addProperty("course_id", courseData.course_id)
        MyConstant.jsonObject.addProperty("workout_id", workout_duration_detail.course_duration_id)
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
        MyConstant.jsonObject.addProperty(
            "workout_exercise_id",
            workout_duration_detail.course_duration_exercise.get(0).course_duration_exercise_id
        )
        MyConstant.jsonObject.addProperty("exercise_status", "started")


        binding.btnStartWorkout.setOnClickListener {
            val gson = Gson()
            startWorkoutApi(MyConstant.jsonObject)
            startActivity(Intent(this@WorkOutDetailScreen, SlideWorkoutVideoActivity::class.java).putExtra(
                "duration_work_position",
                gson.toJson(courseData.course_detail.course_duration.get(0))
            ).putExtra("course_data", gson.toJson(courseData)))
        }

//            startActivity(Intent(this@WorkOutDetailScreen, PlayWorkoutVideoActivity::class.java)) }
    }

    fun startWorkoutApi(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
//            showLoader()
            authViewModel.courseStartApiCall("Bearer " + sharedPreferences.token, jsonObject)
            authViewModel.courseStartResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.courseStartResponse.removeObservers(this)
                        if (authViewModel.courseStartResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.status) {
                                    showToast(response.message)
//                                    startActivity(
//                                        Intent(
//                                            this@WorkOutDetailScreen,
//                                            OrderCompleteActivity::class.java
//                                        ).putExtra("message", response.message)
//                                    )
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.courseStartResponse.removeObservers(this)
                        if (authViewModel.courseStartResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewBinding() = ActivityWorkOutDetailScreenBinding.inflate(layoutInflater)
}