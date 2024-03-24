package com.dev.batchfinal.app_modules.workout_motivator.view

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityWorkOutDetailScreenBinding
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.scanning.work_manager.VimeoVideoWorker
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import kotlin.system.exitProcess


@AndroidEntryPoint
class WorkOutDetailScreen : BaseActivity<ActivityWorkOutDetailScreenBinding>() {
    private var workout_duration_detail: Course_duration? = null
    private var courseData: OrderList? = null
    private val viewModel: AllViewModel by viewModels()

    private val mVimeoURLs=ArrayList<HashMap<String,String>>()

    private val authViewModel by viewModels<AuthViewModel>()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        try {
            val gson = Gson()
            var strObj: String? = null
            if (intent != null) {

                if (intent.hasExtra("duration_work_position")) {
                    strObj = intent.getStringExtra("duration_work_position").toString()
                }
                if (strObj?.isNotEmpty() == true) {
                    workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
                }


            }
            var strObj1 = ""
            if (intent.hasExtra("course_data"))
            {
                strObj1 = intent.getStringExtra("course_data").toString()

                if (strObj1.isNotEmpty()) {
                    courseData = gson.fromJson(strObj1, OrderList::class.java)
                }
            }

            if (strObj?.isNotEmpty() == true) {
                workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
            }

            for (i in 0 until courseData!!.course_detail.course_duration.size) {
                for (j in 0 until courseData!!.course_detail.course_duration[i].course_duration_exercise.size) {
                    // Perform operations here
                    println("Row: $i, Column: $j")
                    val map=HashMap<String,String>()
                    if (!courseData!!.course_detail.course_duration[i].course_duration_exercise[j].video_detail.video_id.isNullOrEmpty())
                    {
                        map["videoKey"] = courseData!!.course_detail.course_duration[i].course_duration_exercise[j].video_detail.video_id.toString()
                        map["videoId"] = courseData!!.course_detail.course_duration[i].course_duration_exercise[j].course_duration_exercise_id.toString()
                        mVimeoURLs.add(map)
                    }

                }
                exitProcess(0)
            }

        Log.e("V_Key",mVimeoURLs.toString())
            binding.weightLossText.text = workout_duration_detail?.day_name
            binding.txtDetailContent.text = workout_duration_detail?.description
            binding.userName.text = courseData?.course_detail?.coach_detail?.name.toString()
            MyUtils.loadImage(
                binding.profileImage,
                MyConstant.IMAGE_BASE_URL + courseData?.course_detail?.coach_detail?.profile_photo_path
            )

            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData?.course_detail?.course_image
            )
            buttonClicks()
            startRelativeAnimation(binding.relWeightDetailLayout)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun buttonClicks() {

        try {
            MyConstant.jsonObject.addProperty("course_id", courseData?.course_id)
            MyConstant.jsonObject.addProperty(
                "workout_id",
                workout_duration_detail?.course_duration_id
            )
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
            var mworkout_exercise_id = ""
            if (workout_duration_detail?.course_duration_exercise != null && workout_duration_detail?.course_duration_exercise?.size!! > 0) {
                mworkout_exercise_id =
                    workout_duration_detail?.course_duration_exercise?.get(0)!!.course_duration_exercise_id.toString()
            }

            MyConstant.jsonObject.addProperty(
                "workout_exercise_id",
                mworkout_exercise_id
            )
            MyConstant.jsonObject.addProperty("exercise_status", "started")

            /***
             * START WORKOUT VIDEO
             * */
            binding.btnStartWorkout.setOnClickListener {
                val gson = Gson()
                var mCouseduration = ""
                var mCourseData = ""
                startWorkoutApi(MyConstant.jsonObject)

                val mIntent = Intent(this@WorkOutDetailScreen, SlideWorkoutVideoActivity::class.java)
                if (courseData?.course_detail?.course_duration != null && courseData?.course_detail?.course_duration?.size!! > 0) {
                    mCouseduration = gson.toJson(courseData?.course_detail?.course_duration?.get(0)!!)
                }
                if (courseData != null) {
                    mCourseData = gson.toJson(courseData)
                }
                // NOTE - NEW WAY IMPLEMENTATION OF VIMEO-IN PROGRESS
                //   val videoKey= courseData?.course_detail?.course_duration?.get(0)!!.course_duration_exercise[0].video_id
                // Log.e("VIDEO_KEY",videoKey)



                 val inputData = workDataOf("videoKey" to "911682062")
                 val workRequest = OneTimeWorkRequest.Builder(VimeoVideoWorker::class.java)
                    .setInputData(inputData)
                    .build()
                // Enqueue the WorkRequest
                WorkManager.getInstance(this@WorkOutDetailScreen).enqueue(workRequest)
                mIntent.putExtra("duration_work_position", mCouseduration)
                mIntent.putExtra("course_data", mCourseData)
                startActivity(mIntent)

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

//            startActivity(Intent(this@WorkOutDetailScreen, PlayWorkoutVideoActivity::class.java)) }
    }

    private fun startWorkoutApi(jsonObject: JsonObject) {
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

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewBinding() = ActivityWorkOutDetailScreenBinding.inflate(layoutInflater)
}