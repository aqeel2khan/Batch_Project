package com.dev.batchfinal.app_modules.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.WorkoutTypeAdapter
import com.dev.batchfinal.adapter.WorkoutTypeListAdapter
//import com.dev.batchfinal.databinding.ActivityWeightLossBinding
import com.dev.batchfinal.`interface`.PositionCourseWorkoutClick
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDuration
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.List
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.WorkoutType
import com.dev.batchfinal.app_modules.workout_motivator.model.course_details.CourseDetailResponseModel
import com.dev.batchfinal.app_modules.workout_motivator.view.SlideWorkoutVideoActivity
import com.dev.batchfinal.app_modules.workout_motivator.view.WorkOutDetailScreen
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.databinding.ActivityWeightLossBinding
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class WeightLossActivity : BaseActivity<ActivityWeightLossBinding>() {
    private  var courseData: List?= null
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var REQUEST_CODE = 1234
    private lateinit var courseDetailData: CourseDetailResponseModel.Data
    var name = ArrayList(
        Arrays.asList(
            "Workout Batch",
            "Weight Loss",
            "Workout Batch",
            "Workout Batch",
            "Workout Batch",
            "Workout Batch"
        )
    )

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        try {
            val gson = Gson()
            var strObj= ""
            if(intent!=null){
                if(intent.hasExtra("order_list")){
                    strObj = intent.getStringExtra("order_list").toString()
                }
                if(strObj.isNotEmpty()){
                    courseData = gson.fromJson(strObj, List::class.java)
                }
            }



            binding.weightLossText.text = courseData?.courseDetail?.courseName?:""
            binding.messageText.text = courseData?.courseDetail?.description?.toString()
            binding.userName.text = courseData?.courseDetail?.coachDetail?.name?.toString()
            binding.txtLevel.text = courseData?.courseDetail?.courseLevel?.levelName?.toString()
            binding.levelType.text = courseData?.courseDetail?.courseLevel?.levelName?.toString()

            setWorkoutType(courseData?.courseDetail!!.workoutType )

            binding.tvExerciseDay.text = ("Day " + courseData?.todayWorkouts?.row)
            MyUtils.loadImage(
                binding.profileImage,
                (MyConstant.IMAGE_BASE_URL + courseData?.courseDetail?.coachDetail?.profilePhotoPath)

            )


            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData?.courseDetail?.courseImage
            )


            buttonClicks()
            val aniSlide: Animation =
                AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
            binding.relWeightLayout.startAnimation(aniSlide)

        //    setVideoOnBanner()

            setWorkoutTypeAdapter()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    private fun setWorkoutType(workoutType: MutableList<WorkoutType>) {
        binding.workType.apply {
            layoutManager = LinearLayoutManager(
                this@WeightLossActivity,
                LinearLayoutManager.HORIZONTAL,
                false)
            adapter = WorkoutTypeListAdapter(this@WeightLossActivity, workoutType)
        }
    }


    @SuppressLint("SuspiciousIndentation")
    private fun setVideoOnBanner() {

        try {

            binding.backgroundImg.setOnClickListener {

                binding.videoLayout.visibility = View.VISIBLE


                val courseDurationList = courseData?.courseDetail?.courseDuration
                if (!courseDurationList.isNullOrEmpty()) {

                    val courseDurationData = courseDurationList[0]
                    if (courseDurationData != null && !courseDurationData.courseDurationExercise.isNullOrEmpty()) {

                        val videoId =
                            courseDurationData?.courseDurationExercise?.get(0)?.videoDetail?.videoId
                        if (videoId != null) {

                            val videoIdInt = videoId.toInt()

                            binding.vimeoPlayerView.initialize(true, videoIdInt)

                        }
                    }


                }


            }



        } catch (e: Exception) {
           e.printStackTrace()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val playAt = data!!.getFloatExtra(VimeoPlayerActivity.RESULT_STATE_VIDEO_PLAY_AT, 0f)
            binding.vimeoPlayerView.seekTo(playAt)

            val playerState = PlayerState.valueOf(data!!.getStringExtra(VimeoPlayerActivity.RESULT_STATE_PLAYER_STATE)!!)
            when (playerState) {
                PlayerState.PLAYING -> binding.vimeoPlayerView.play()
                PlayerState.PAUSED -> binding.vimeoPlayerView.pause()
                else -> {}
            }
        }
    }


    private fun setWorkoutTypeAdapter() {
        binding.recyclerWorkoutType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter = WorkoutTypeAdapter(this@WeightLossActivity,
            courseData?.courseDetail?.courseDuration,
            courseData?.todayWorkouts?.row!!,
            object :
                PositionCourseWorkoutClick<Int> {
                override fun onCourseWorkoutItemPosition(item: CourseDuration, postions: Int) {
                    var pos=postions+1
//                    startActivity(
//                        Intent(
//                            this@WeightLossActivity,
//                            CourseDetailActivity::class.java
//                        ).putExtra("course_id", item.course_id.toString())
//                    )

                    if (item.status?.toInt() ==0){
                        showToast("Today is off")

                    }else{

                        val gson = Gson()
                        var mIntent=   Intent(this@WeightLossActivity, SlideWorkoutVideoActivity::class.java)

                        var mCouseduration=""
                        if( courseData?.courseDetail?.courseDuration!=null && courseData?.courseDetail?.courseDuration?.size!!>0){
                            mCouseduration=  gson.toJson(courseData?.courseDetail?.courseDuration?.get(0)!!)
                        }

                        var mCourseData=""
                        if(courseData!=null ){
                            mCourseData =  gson.toJson(courseData)
                        }
                        mIntent.putExtra("duration_work_position",mCouseduration)
                        mIntent.  putExtra("course_data", mCourseData)
                        mIntent.  putExtra("position",pos.toString() )
                        startActivity(mIntent)
                    }



                }

            })
    }

    @SuppressLint("SuspiciousIndentation")
    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
            finish()
        }
        binding.txtChangeCourse.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.startWorkout.setOnClickListener {
            if(courseData!!.todayWorkouts.status.toInt()==0){
                showToast("Today is off")

            }else{
                try {
                    val gson = Gson()
                    var data2 = ""
                    if (courseData != null) {
                        data2 = gson.toJson(courseData)
                    }

                    var mIntent=   Intent(this@WeightLossActivity, WorkOutDetailScreen::class.java)
                    if (courseData != null && courseData?.courseDetail != null && courseData?.courseDetail?.courseDuration != null && courseData?.courseDetail?.courseDuration!!.size>0) {
                        val   data = gson.toJson(courseData?.courseDetail!!.courseDuration[0]!!)

                        mIntent. putExtra("duration_work_position", data)
                    }

                    if(data2!=null) {
                        if (courseData != null) {
                            val   data2 = gson.toJson(courseData)
                            mIntent.putExtra("course_data", data2)
                        }
                        mIntent.putExtra("course_data", data2)
                    }

                    startActivity(mIntent)

//                startActivity(
//                    Intent(this@WeightLossActivity, WorkOutDetailScreen::class.java).putExtra(
//                        "duration_work_position",
//                        gson.toJson(data)
//                    ).putExtra("course_data", data2))

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }


        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        val ll_bottom_change_course =
            dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        ll_bottom_change_course!!.visibility = View.VISIBLE


        dialog.show()
    }


    override fun getViewBinding() = ActivityWeightLossBinding.inflate(layoutInflater)
}