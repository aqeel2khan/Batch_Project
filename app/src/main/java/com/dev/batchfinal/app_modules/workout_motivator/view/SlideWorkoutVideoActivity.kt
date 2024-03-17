package com.dev.batchfinal.app_modules.workout_motivator.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
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
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.databinding.DialogVideoInfoBinding
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog

@AndroidEntryPoint
class SlideWorkoutVideoActivity : BaseActivity<ActivitySlideWorkoutVideoBinding>() {
    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    private  var workout_duration_detail: Course_duration?=null
    private lateinit var courseData: OrderList

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    private fun setVideoOnBanner() {

        // CourseDetail > CourseDuration List =  Total Duration > 1 Day > get 0 index > course duration excersize for 1 day
        val course_duration_exerciseList : ArrayList<Course_duration_exercise>
        val courseDurationList=  courseData.course_detail.course_duration
        if(courseDurationList.isNotEmpty()){
            val courseDurationData= courseDurationList[0] // first day
            if(courseDurationData!=null && !courseDurationData.course_duration_exercise.isNullOrEmpty()){
                // Get First Day all video
                course_duration_exerciseList=      courseDurationData?.course_duration_exercise!!

                if(!course_duration_exerciseList.isNullOrEmpty()){
                    binding.viewPagerVideos.adapter = VideosAdapter(this@SlideWorkoutVideoActivity,course_duration_exerciseList, object :
                            ItemVideoFinishClick<Int> {

                            override fun onPositionItemVideoFinish(item: Course_duration_exercise, postions: Int) {
                                try {
                                    MyConstant.jsonObject.addProperty("course_id", courseData.course_id)
                                    MyConstant.jsonObject.addProperty("workout_id", workout_duration_detail?.course_duration_id)
                                    MyConstant.jsonObject.addProperty("workout_exercise_id", workout_duration_detail?.course_duration_exercise!![0].course_duration_exercise_id)
                                    MyConstant.jsonObject.addProperty("exercise_status", "completed")
                                    updateFinishWorkoutStaus(MyConstant.jsonObject)
                                }catch (_:Exception){}

                            }

                        override fun onClickVideoInformation(
                            item: Course_duration_exercise,
                            postions: Int
                        ) {
                            showVideoInformation(item)
                        }

                        override fun onCloseVideoView(
                            item: Course_duration_exercise,
                            postions: Int
                        ) {
                            finish()
                        }

                        })
                }

                val videoId=      courseDurationData?.course_duration_exercise?.get(0)?.video_detail?.video_id
                if(videoId!=null){
                    var videoIdInt=   videoId.toInt()
                }
            }



        }


    }

    @SuppressLint("SetTextI18n")
    private fun showVideoInformation(item: Course_duration_exercise) {

      val  dialogVideoInfoBinding = DialogVideoInfoBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogVideoInfoBinding.root)
        dialogVideoInfoBinding.tvVideoShortTitle.text=""+item.title
        when {
            !item.description.isNullOrEmpty() -> {
                dialogVideoInfoBinding.tvVideoDescription.text=""+item.description
                dialogVideoInfoBinding.tvVideoDescriptionTxt.visibility= View.VISIBLE
                dialogVideoInfoBinding.tvVideoDescription.visibility= View.VISIBLE


            }
            else -> {
                dialogVideoInfoBinding.tvVideoDescription.visibility= View.GONE
                dialogVideoInfoBinding.tvVideoDescriptionTxt.visibility= View.GONE

            }
        }
        when {
            !item.instruction.isNullOrEmpty() -> {
                dialogVideoInfoBinding.tvVideoInstruction.text=""+item.instruction
                dialogVideoInfoBinding.tvVideoInstruction.visibility= View.VISIBLE
                dialogVideoInfoBinding.tvVideoInstructionTxt.visibility= View.VISIBLE


            }
            else -> {
                dialogVideoInfoBinding.tvVideoInstruction.visibility= View.GONE
                dialogVideoInfoBinding.tvVideoInstructionTxt.visibility= View.GONE

            }
        }

        dialogVideoInfoBinding.closeVideoInfo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    override fun initUi() {

        val videoItems: MutableList<VideoItem> = ArrayList<VideoItem>()

        val gson = Gson()

        var strObj= ""
        if(intent.hasExtra("duration_work_position")){
            strObj = intent.getStringExtra("duration_work_position").toString()
        }

        if(strObj.isNotEmpty()){
            workout_duration_detail = gson.fromJson(strObj, Course_duration::class.java)
        }

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