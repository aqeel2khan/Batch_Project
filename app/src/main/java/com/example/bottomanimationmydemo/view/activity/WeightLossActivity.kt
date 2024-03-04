package com.example.bottomanimationmydemo.view.activity

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
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.WorkoutTypeAdapter
import com.example.bottomanimationmydemo.databinding.ActivityWeightLossBinding
import com.example.bottomanimationmydemo.`interface`.PositionCourseWorkoutClick
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class WeightLossActivity : BaseActivity<ActivityWeightLossBinding>() {
    private lateinit var courseData: OrderList
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var REQUEST_CODE = 1234
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
        val gson = Gson()
        val strObj = intent.getStringExtra("order_list")
        courseData = gson.fromJson(strObj, OrderList::class.java)
//        getCourseDetailData(courseData.course_detail.course_id)
        binding.weightLossText.text = courseData.course_detail.course_name
        binding.messageText.text = courseData.course_detail.description.toString()
        binding.userName.text = courseData.course_detail.coach_detail.name.toString()
        binding.txtLevel.text = courseData.course_detail.course_level.level_name.toString()
        binding.tvExerciseDay.text = "day " + courseData.course_detail.duration
        MyUtils.loadImage(binding.profileImage, MyConstant.IMAGE_BASE_URL + courseData.course_detail.coach_detail.profile_photo_path)

        buttonClicks()
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.relWeightLayout.startAnimation(aniSlide)

        setVideoOnBanner()

        setWorkoutTypeAdapter()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setVideoOnBanner() {

       val courseDurationList=  courseData.course_detail.course_duration
        if(!courseDurationList.isNullOrEmpty()){

         val courseDurationData= courseDurationList[0]
            if(courseDurationData!=null && !courseDurationData.course_duration_exercise.isNullOrEmpty()){

                val videoId=      courseDurationData?.course_duration_exercise?.get(0)?.video_detail?.video_id
                if(videoId!=null){

                 var videoIdInt=   videoId.toInt()


                    binding.vimeoPlayerView.initialize(true, videoIdInt)

                    binding.vimeoPlayerView.defaultControlPanelView.vimeoPlayButton.visibility = View.INVISIBLE

                    //vimeoPlayerView.initialize(true, {YourPrivateVideoId}, "SettingsEmbeddedUrl")
                    //vimeoPlayerView.initialize(true, {YourPrivateVideoId},"VideoHashKey", "SettingsEmbeddedUrl")

                    binding.vimeoPlayerView.addTimeListener { second ->
//                        binding.playerCurrentTimeTextView.text =
//                            getString(R.string.player_current_time, second.toString())
                    }

                    binding.vimeoPlayerView.addErrorListener { message, method, name ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }

                    binding.vimeoPlayerView.addReadyListener(object : VimeoPlayerReadyListener {
                        override fun onReady(
                            title: String?,
                            duration: Float,
                            textTrackArray: Array<TextTrack>
                        ) {
                         //   binding.vimeoPlayerView.play()
                         //   binding.playerStateTextView.text = getString(R.string.player_state, "onReady")
                        }

                        override fun onInitFailed() {
                          //  binding.playerStateTextView.text = getString(R.string.player_state, "onInitFailed")
                        }
                    })

                    binding.vimeoPlayerView.addStateListener(object : VimeoPlayerStateListener {
                        override fun onPlaying(duration: Float) {
//                            binding.playerStateTextView.text = getString(R.string.player_state, "onPlaying")
                        }

                        override fun onPaused(seconds: Float) {
//                            binding.playerStateTextView.text = getString(R.string.player_state, "onPaused")
                        }

                        override fun onEnded(duration: Float) {
//                            binding.playerStateTextView.text = getString(R.string.player_state, "onEnded")
                        }
                    })
                    binding.vimeoPlayerView.addVolumeListener { volume ->
//                        binding.playerVolumeTextView.text = getString(R.string.player_volume, volume.toString())
                    }

                    binding.vimeoPlayerView.setFullscreenClickListener {
                        var requestOrientation = VimeoPlayerActivity.REQUEST_ORIENTATION_AUTO
                        startActivityForResult(VimeoPlayerActivity.createIntent(this, requestOrientation, binding.vimeoPlayerView), REQUEST_CODE)
                    }



                }
            }


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            var playAt = data!!.getFloatExtra(VimeoPlayerActivity.RESULT_STATE_VIDEO_PLAY_AT, 0f)
            binding.vimeoPlayerView.seekTo(playAt)

            var playerState = PlayerState.valueOf(data!!.getStringExtra(VimeoPlayerActivity.RESULT_STATE_PLAYER_STATE)!!)
            when (playerState) {
                PlayerState.PLAYING -> binding.vimeoPlayerView.play()
                PlayerState.PAUSED -> binding.vimeoPlayerView.pause()
                else -> {}
            }
        }
    }



    private fun setWorkoutTypeAdapter() {
        binding.recyclerWorkoutType.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter = WorkoutTypeAdapter(this@WeightLossActivity,
            courseData.course_detail.course_duration,
            courseData.course_detail.duration,
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

    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
            onBackPressed()
            finish()
        }
        binding.txtChangeCourse.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.startWorkout.setOnClickListener {
            val gson = Gson()
            startActivity(
                Intent(this@WeightLossActivity, WorkOutDetailScreen::class.java).putExtra(
                    "duration_work_position",
                    gson.toJson(courseData.course_detail.course_duration.get(0))
                ).putExtra("course_data", gson.toJson(courseData))
            )
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