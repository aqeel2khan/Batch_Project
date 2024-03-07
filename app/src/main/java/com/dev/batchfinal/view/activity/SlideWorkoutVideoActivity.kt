package com.dev.batchfinal.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.VideosAdapter
import com.dev.batchfinal.databinding.ActivitySlideWorkoutVideoBinding
import com.dev.batchfinal.`interface`.ItemVideoFinishClick
import com.dev.batchfinal.model.VideoItem
import com.dev.batchfinal.model.courseorderlist.Course_duration
import com.dev.batchfinal.model.courseorderlist.Course_duration_exercise
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.utils.CheckNetworkConnection
import com.dev.batchfinal.utils.MyConstant
import com.dev.batchfinal.utils.MyCustom
import com.dev.batchfinal.utils.showToast
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class SlideWorkoutVideoActivity : BaseActivity<ActivitySlideWorkoutVideoBinding>() {
    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var workout_duration_detail: Course_duration
    private lateinit var courseData: OrderList

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    private fun setVideoOnBanner() {

        // CourseDetail > CourseDuration List =  Total Duration > 1 Day > get 0 index > course duration excersize for 1 day
        var course_duration_exerciseList : ArrayList<Course_duration_exercise>
        val courseDurationList=  courseData.course_detail.course_duration
        if(!courseDurationList.isNullOrEmpty()){


            val courseDurationData= courseDurationList[0] // first day
            if(courseDurationData!=null && !courseDurationData.course_duration_exercise.isNullOrEmpty()){
                // Get First Day all video
                course_duration_exerciseList=      courseDurationData?.course_duration_exercise!!

                if(!course_duration_exerciseList.isNullOrEmpty()){
                    binding.viewPagerVideos.setAdapter(VideosAdapter(course_duration_exerciseList, object :
                        ItemVideoFinishClick<Int> {

                        override fun onPositionItemVideoFinish(item: Course_duration_exercise, postions: Int) {
                            MyConstant.jsonObject.addProperty("course_id", courseData.course_id)
                            MyConstant.jsonObject.addProperty(
                                "workout_id",
                                workout_duration_detail.course_duration_id
                            )
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
                            MyConstant.jsonObject.addProperty(
                                "workout_exercise_id",
                                workout_duration_detail.course_duration_exercise.get(0).course_duration_exercise_id
                            )
                            MyConstant.jsonObject.addProperty("exercise_status", "completed")


                            updateFinishWorkoutStaus(MyConstant.jsonObject)
                        }

                    }))
                }

                val videoId=      courseDurationData?.course_duration_exercise?.get(0)?.video_detail?.video_id
                if(videoId!=null){
                    var videoIdInt=   videoId.toInt()
                }
            }



        }


    }


    override fun initUi() {

        val videoItems: MutableList<VideoItem> = ArrayList<VideoItem>()

        val gson = Gson()
        val strObj = intent.getStringExtra("duration_work_position")
        workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)

        val strObj1 = intent.getStringExtra("course_data")
        courseData = gson.fromJson(strObj1, OrderList::class.java)

        val item = VideoItem()
        item.videoURL = "https://player.vimeo.com/video/913068330"
        item.videoTitle = "Women In Tech"
        videoItems.add(item)

        val item2 = VideoItem()
        item2.videoURL = "https://player.vimeo.com/video/913068229"
        item2.videoTitle = "Sasha Solomon"
        videoItems.add(item2)

        val item3 = VideoItem()
        item3.videoURL = "https://player.vimeo.com/video/911555330"
        item3.videoTitle = "Happy Hour Wednesday"
        videoItems.add(item3)

        val item4 = VideoItem()
        item4.videoURL = "https://player.vimeo.com/video/913068330"
        item4.videoTitle = "Happy 4 Wednesday"
        videoItems.add(item4)

        setVideoOnBanner()

    }

    fun updateFinishWorkoutStaus(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
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
                                    startActivity(
                                        Intent(
                                            this@SlideWorkoutVideoActivity,
                                            ComplateWorkoutActivity::class.java
                                        ).putExtra("message", response.message)
                                    )
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

    override fun getViewBinding() = ActivitySlideWorkoutVideoBinding.inflate(layoutInflater)
}