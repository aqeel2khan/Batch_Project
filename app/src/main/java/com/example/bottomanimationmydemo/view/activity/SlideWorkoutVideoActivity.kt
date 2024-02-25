package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.VideosAdapter
import com.example.bottomanimationmydemo.databinding.ActivitySlideWorkoutVideoBinding
import com.example.bottomanimationmydemo.`interface`.ItemVideoFinishClick
import com.example.bottomanimationmydemo.`interface`.PositionCourseWorkoutClick
import com.example.bottomanimationmydemo.model.VideoItem
import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class SlideWorkoutVideoActivity : BaseActivity<ActivitySlideWorkoutVideoBinding>() {
    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    private lateinit var workout_duration_detail: Course_duration
    private lateinit var courseData: OrderList

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {

        val videoItems: MutableList<VideoItem> = ArrayList<VideoItem>()

        val gson = Gson()
        val strObj = intent.getStringExtra("duration_work_position")
        workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)

        val strObj1 = intent.getStringExtra("course_data")
        courseData = gson.fromJson(strObj1, OrderList::class.java)

        val item = VideoItem()
        item.videoURL = "https://player.vimeo.com/video/1084537"
        item.videoTitle = "Women In Tech"
        videoItems.add(item)

        val item2 = VideoItem()
        item2.videoURL = "https://player.vimeo.com/video/347119375"
        item2.videoTitle = "Sasha Solomon"
        videoItems.add(item2)

        val item3 = VideoItem()
        item3.videoURL = "https://player.vimeo.com/video/252443587"
        item3.videoTitle = "Happy Hour Wednesday"
        videoItems.add(item3)

        val item4 = VideoItem()
        item4.videoURL = "https://player.vimeo.com/video/449787858"
        item4.videoTitle = "Happy 4 Wednesday"
        videoItems.add(item4)

        binding.viewPagerVideos.setAdapter(VideosAdapter(videoItems, object :
            ItemVideoFinishClick<Int> {

            override fun onPositionItemVideoFinish(item: VideoItem, postions: Int) {
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