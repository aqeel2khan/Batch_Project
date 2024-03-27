package com.dev.batchfinal.app_modules.workout_motivator.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager

import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityWorkOutDetailScreenBinding
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDuration
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.List
import com.dev.batchfinal.app_modules.scanning.view.ExoPlayerActivity
import com.dev.batchfinal.app_modules.scanning.work_manager.VimeoVideoWorker
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import com.google.gson.reflect.TypeToken
import java.util.ArrayList
import java.util.HashMap

@AndroidEntryPoint
class WorkOutDetailScreen : BaseActivity<ActivityWorkOutDetailScreenBinding>() {
    private  var courseData: List?= null
    private val mVimeoURLs = ArrayList<HashMap<String, String>>()

    private val viewModel: AllViewModel by viewModels()


    private val authViewModel by viewModels<AuthViewModel>()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        try {
            val gson = Gson()
            var strObj :String?= null
            strObj = intent.getStringExtra("TODAY_WORKOUTS").toString()
            if(strObj.isNotEmpty()){
                courseData = gson.fromJson(strObj, List::class.java)
            }
            setWorkoutDetails()
            buttonClicks()
            startRelativeAnimation(binding.relWeightDetailLayout)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun setWorkoutDetails()
    {
        binding.weightLossText.text = courseData!!.todayWorkouts?.dayName
        binding.txtDetailContent.text = courseData!!.todayWorkouts?.description
        binding.userName.text = courseData?.courseDetail?.coachDetail?.name.toString()
        binding.txtExercise.text = courseData?.todayWorkouts!!.noOfExercise.toString()+"Exercise"
        binding.txtTime.text = courseData?.todayWorkouts!!.workoutTime.toString()
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
    }

    private fun buttonClicks() {

        try {
            MyConstant.jsonObject.addProperty("course_id", courseData?.courseId)
            MyConstant.jsonObject.addProperty("workout_id", courseData!!.todayWorkouts?.courseDurationId)
            var mworkout_exercise_id= ""
            if(courseData!!.todayWorkouts?.courseDurationExercise!=null && courseData!!.todayWorkouts?.courseDurationExercise?.size!!>0){
                mworkout_exercise_id= courseData!!.todayWorkouts?.courseDurationExercise?.get(0)!!.courseDurationExerciseId.toString()
            }
            MyConstant.jsonObject.addProperty("workout_exercise_id", mworkout_exercise_id)
            MyConstant.jsonObject.addProperty("exercise_status", "started")


            binding.btnStartWorkout.setOnClickListener {

                mVimeoURLs.clear()
                showLoader()

                for (j in 0 until courseData!!.todayWorkouts.courseDurationExercise.size) {
                    // Perform operations here

                    val map = HashMap<String, String>()
                    if (!courseData!!.todayWorkouts.courseDurationExercise[j].videoDetail.videoId.isNullOrEmpty()) {
                        map["videoUrl"] = courseData!!.todayWorkouts.courseDurationExercise[j].videoDetail.videoId.toString()
                        map["videoKey"] = courseData!!.todayWorkouts.courseDurationExercise[j].videoDetail.videoId.toString()
                        map["videoId"] = courseData!!.todayWorkouts.courseDurationExercise[j].courseDurationExerciseId.toString()

                        mVimeoURLs.add(map)
                    }
                }
                Log.e("V_Key", mVimeoURLs.toString())

                // NOTE - NEW WAY IMPLEMENTATION OF VIMEO-IN PROGRESS

                //val inputData = workDataOf("videoKey" to "911682062")
                val serializedData = Gson().toJson(mVimeoURLs)
                val inputData = Data.Builder()
                    .putString("dataList", serializedData)
                    .build()

                // val inputData = workDataOf(mVimeoURLs)

                val workRequest = OneTimeWorkRequest.Builder(VimeoVideoWorker::class.java)
                    .setInputData(inputData)
                    .build()
                // Enqueue the WorkRequest
                WorkManager.getInstance(this@WorkOutDetailScreen).enqueue(workRequest)


                WorkManager.getInstance(applicationContext)
                    .getWorkInfoByIdLiveData(workRequest.id)
                    .observe(
                        this@WorkOutDetailScreen,
                        androidx.lifecycle.Observer { workInfo ->
                            if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                                // Retrieve the output data from Worker
                                val dataListJson = workInfo.outputData.getString("dataList")

                                // Deserialize the dataListJson to ArrayList<HashMap<String, String>>
                                val dataListType = object :
                                    TypeToken<ArrayList<HashMap<String, String>>>() {}.type
                                val dataList: ArrayList<HashMap<String, String>> =
                                    Gson().fromJson(dataListJson, dataListType)
                                Log.e("VIMEO DATA LIST", dataList.toString())


                                Handler(Looper.getMainLooper()).postDelayed({
                                    val gson = Gson()
                                    val mIntent = Intent(
                                        this@WorkOutDetailScreen,
                                        ExoPlayerActivity::class.java
                                    )

                                    mIntent.putExtra("today_course_data", Gson().toJson(courseData!!.todayWorkouts))
                                    mIntent.putExtra("video_link",gson.toJson(dataList))
                                    mIntent.putExtra("TARGETED_FROM","WorkoutDetails")
                                    startActivity(mIntent)
                                    hideLoader()
                                }, 3000)




                            }
                        })
                //Need to change flow from here

               /* val gson = Gson()
                startWorkoutApi(MyConstant.jsonObject)
                val mIntent=   Intent(this@WorkOutDetailScreen, SlideWorkoutVideoActivity::class.java)
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
                startActivity(mIntent)*/

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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