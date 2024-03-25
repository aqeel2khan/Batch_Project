package com.dev.batchfinal.app_modules.workout_motivator.view

import android.content.Intent
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope

import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityWorkOutDetailScreenBinding
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.List
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
    private  var workout_duration_detail: Course_duration?= null
    private  var courseData: List?= null
    private val viewModel: AllViewModel by viewModels()


    private val authViewModel by viewModels<AuthViewModel>()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        try {
            val gson = Gson()
            var strObj :String?= null
            if(intent!=null){
                if(intent.hasExtra("duration_work_position"))
                {
                    strObj = intent.getStringExtra("duration_work_position").toString()
                }
                if(strObj?.isNotEmpty() == true)
                {
                    workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
                }


            }
            var strObj1 =""
            if(intent.hasExtra("course_data")){
                strObj1 = intent.getStringExtra("course_data").toString()

                if(strObj1.isNotEmpty()){
                    courseData = gson.fromJson(strObj1, List::class.java)
                }
            }

            if(strObj?.isNotEmpty() == true){
                workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
            }


            binding.weightLossText.text = workout_duration_detail?.day_name
            binding.txtDetailContent.text = workout_duration_detail?.description
            binding.userName.text = courseData?.courseDetail?.coachDetail?.name.toString()
            binding.txtExercise.text = courseData?.todayWorkouts!!.noOfExercise.toString()+"Exercise"
            binding.txtTime.text = courseData?.todayWorkouts!!.workoutTime.toString()+"min"
            binding.txtCal.text = courseData?.todayWorkouts!!.calorieBurn.toString()+"Kcal"
            binding.txtDetailContent.text = courseData?.todayWorkouts!!.description
            MyUtils.loadImage(
                binding.profileImage,
                MyConstant.IMAGE_BASE_URL + courseData?.courseDetail?.coachDetail?.profilePhotoPath
            )

            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData?.courseDetail?.courseImage
            )
            buttonClicks()
            startRelativeAnimation(binding.relWeightDetailLayout)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun buttonClicks() {

        try {
            MyConstant.jsonObject.addProperty("course_id", courseData?.courseId)
            MyConstant.jsonObject.addProperty("workout_id", workout_duration_detail?.course_duration_id)
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
            var mworkout_exercise_id= ""
            if(workout_duration_detail?.course_duration_exercise!=null && workout_duration_detail?.course_duration_exercise?.size!!>0){
                mworkout_exercise_id= workout_duration_detail?.course_duration_exercise?.get(0)!!.course_duration_exercise_id.toString()
            }
            MyConstant.jsonObject.addProperty("workout_exercise_id", mworkout_exercise_id)
            MyConstant.jsonObject.addProperty("exercise_status", "started")

/*
            for (i in 0 until courseData!!.courseDetail.courseDuration.size) {
                for (j in 0 until courseData!!.courseDetail.courseDuration[i].courseDurationExercise.size) {
                    // Perform operations here
                    println("Row: $i, Column: $j")
                    val map=HashMap<String,String>()
                    if (!courseData!!.courseDetail.courseDuration[i].courseDurationExercise[j].videoDetail.videoId.isNullOrEmpty())
                    {
                        map["videoKey"] = courseData!!.courseDetail.courseDuration[i].courseDurationExercise[j].videoDetail.videoId.toString()
                        map["videoId"] = courseData!!.courseDetail.courseDuration[i].courseDurationExercise[j].courseDurationExerciseId.toString()
                        mVimeoURLs.add(map)
                    }

                }
                exitProcess(0)
            }
*/






            binding.btnStartWorkout.setOnClickListener {
                val gson = Gson()
                startWorkoutApi(MyConstant.jsonObject)
                var mIntent=   Intent(this@WorkOutDetailScreen, SlideWorkoutVideoActivity::class.java)

                var mCouseduration=""
                if( courseData?.courseDetail?.courseDuration!=null && courseData?.courseDetail?.courseDuration?.size!!>0){
                    mCouseduration=  gson.toJson(courseData?.courseDetail?.courseDuration?.get(0)!!)
                }

                var mCourseData=""
                if(courseData!=null ){
                    mCourseData =  gson.toJson(courseData)
                }

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
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewBinding() = ActivityWorkOutDetailScreenBinding.inflate(layoutInflater)
}