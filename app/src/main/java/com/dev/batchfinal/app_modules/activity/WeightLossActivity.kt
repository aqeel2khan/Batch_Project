package com.dev.batchfinal.app_modules.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerReadyListener
import com.ct7ct7ct7.androidvimeoplayer.listeners.VimeoPlayerStateListener
import com.ct7ct7ct7.androidvimeoplayer.model.PlayerState
import com.ct7ct7ct7.androidvimeoplayer.model.TextTrack
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.WorkoutTypeAdapter
//import com.dev.batchfinal.databinding.ActivityWeightLossBinding
import com.dev.batchfinal.`interface`.PositionCourseWorkoutClick
import com.dev.batchfinal.model.course_detail.Data
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.workout_motivator.view.WorkOutDetailScreen
import com.dev.batchfinal.databinding.ActivityWeightLossBinding
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeightLossActivity : BaseActivity<ActivityWeightLossBinding>() {
    private  var courseData: OrderList?= null
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var REQUEST_CODE = 1234
    private lateinit var courseDetailData: Data
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
                    courseData = gson.fromJson(strObj, OrderList::class.java)
                }
            }



            binding.weightLossText.text = courseData?.course_detail?.course_name?:""
            binding.messageText.text = courseData?.course_detail?.description?.toString()
            binding.userName.text = courseData?.course_detail?.coach_detail?.name?.toString()
            binding.txtLevel.text = courseData?.course_detail?.course_level?.level_name?.toString()
            binding.tvExerciseDay.text = ("day " + courseData?.course_detail?.duration)
            MyUtils.loadImage(
                binding.profileImage,
                (MyConstant.IMAGE_BASE_URL + courseData?.course_detail?.coach_detail?.profile_photo_path)

            )


            MyUtils.loadBackgroundImage(
                binding.backgroundImg,
                MyConstant.IMAGE_BASE_URL + courseData?.course_detail?.course_image
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

    @SuppressLint("SuspiciousIndentation")
    private fun setVideoOnBanner() {

        try {

            binding.backgroundImg.setOnClickListener {

                binding.videoLayout.visibility = View.VISIBLE


                val courseDurationList = courseData?.course_detail?.course_duration
                if (!courseDurationList.isNullOrEmpty()) {

                    val courseDurationData = courseDurationList[0]
                    if (courseDurationData != null && !courseDurationData.course_duration_exercise.isNullOrEmpty()) {

                        val videoId =
                            courseDurationData?.course_duration_exercise?.get(0)?.video_detail?.video_id
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
            courseData?.course_detail?.course_duration,
            courseData?.course_detail?.duration!!,
            object :
                PositionCourseWorkoutClick<Int> {
                override fun onCourseWorkoutItemPosition(item: Course_duration, postions: Int) {
//                    startActivity(
//                        Intent(
//                            this@WeightLossActivity,
//                            CourseDetailActivity::class.java
//                        ).putExtra("course_id", item.course_id.toString())
//                    )
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
            try {
                val gson = Gson()
                var data2 = ""
                if (courseData != null) {
                    data2 = gson.toJson(courseData)
                }

             var mIntent=   Intent(this@WeightLossActivity, WorkOutDetailScreen::class.java)
                    if (courseData != null && courseData?.course_detail != null && courseData?.course_detail?.course_duration != null && courseData?.course_detail?.course_duration!!.size>0) {
                     val   data = gson.toJson(courseData?.course_detail!!.course_duration[0]!!)

                        mIntent. putExtra(
                            "duration_work_position",
                            data)
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