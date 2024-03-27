package com.dev.batchfinal.app_modules.scanning.view

import android.annotation.SuppressLint
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.app_common.AppBaseActivity
import com.dev.batchfinal.app_modules.scanning.adapter.ExoPlayerAdapter
import com.dev.batchfinal.app_modules.scanning.mlistner.PlayerCallback
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDuration
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.CourseDurationExercise
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.List
import com.dev.batchfinal.app_modules.scanning.model.course_order_list.TodayWorkouts
import com.dev.batchfinal.databinding.ActivityExoplayerBinding
import com.dev.batchfinal.databinding.DialogVideoInfoBinding
import com.dev.batchfinal.model.courseorderlist.Course_duration_exercise
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExoPlayerActivity : AppBaseActivity<ActivityExoplayerBinding>(), PlayerCallback {
    private lateinit var dataList: ArrayList<HashMap<String, String>>
   // private var workoutDurationDetail: CourseDuration? = null
    private var courseTodayWorkouts: TodayWorkouts?=null
    private lateinit var courseData: List
    private lateinit var mListner:PlayerCallback

    override fun getViewBinding() = ActivityExoplayerBinding.inflate(layoutInflater)
    override fun initUI() {
        try {
            mListner=this
            window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
            if (intent.getStringExtra("TARGETED_FROM").toString()=="WeightLoss")
            {
                val gson = Gson()
                var jsonStr = ""
                var jsonStr1 = ""
                var jsonStr2 = ""


                /***********************Work Manager Data*********************************/
                if (intent.hasExtra("video_link")) {
                    jsonStr = intent.getStringExtra("video_link").toString()
                }
                if (jsonStr.isNotEmpty()) {
                    dataList = jsonStringToArrayListHashMap(jsonStr)
                }

                /**************************************************************************/
                /*       if (intent.hasExtra("duration_work_position")) {
                           jsonStr1 = intent.getStringExtra("duration_work_position").toString()
                       }


                       if (jsonStr1.isNotEmpty()) {
                           workoutDurationDetail = gson.fromJson(jsonStr1, CourseDuration::class.java)
                       }
               */

                if (intent.hasExtra("course_data")) {
                    jsonStr2 = intent.getStringExtra("course_data").toString()

                }
                if (jsonStr2.isNotEmpty()) {
                    courseData = gson.fromJson(jsonStr2, List::class.java)

                }
            }else if(intent.getStringExtra("TARGETED_FROM").toString()=="WorkoutDetails")
            {
                val gson = Gson()

                var jsonStr = ""
                var jsonStr1 = ""

                /***********************Work Manager Data*********************************/
                if (intent.hasExtra("video_link")) {
                    jsonStr = intent.getStringExtra("video_link").toString()
                }
                if (jsonStr.isNotEmpty()) {
                    dataList = jsonStringToArrayListHashMap(jsonStr)
                }

                /***********************Today Work Data*********************************/

                jsonStr1 = intent.getStringExtra("today_course_data").toString()
                if(jsonStr1.isNotEmpty()){
                    courseTodayWorkouts = gson.fromJson(jsonStr1, TodayWorkouts::class.java)
                }

            }



        } catch (_: Exception) {
        }


    }

    override fun onStart() {
        super.onStart()
        when {
            intent.getStringExtra("TARGETED_FROM").toString()=="WeightLoss" -> {
                val position = intent.getStringExtra("position")
                val courseDurationExcerciseList: ArrayList<CourseDurationExercise>
                val courseDurationList = courseData.courseDetail.courseDuration
                if (courseDurationList.isNotEmpty()) {
                    val courseDurationData = courseDurationList[position!!.toInt()]
                    if (courseDurationData != null && !courseDurationData.courseDurationExercise.isNullOrEmpty()) {
                        courseDurationExcerciseList = (courseDurationData.courseDurationExercise as ArrayList<CourseDurationExercise>?)!!
                        if (courseDurationExcerciseList.isNotEmpty()) {
                            val mPlayerAdapter = ExoPlayerAdapter(this, dataList, courseDurationExcerciseList,mListner)
                            val manager = LinearLayoutManager(this)
                            manager.orientation = LinearLayoutManager.VERTICAL
                            binding.rvExoPlayer.layoutManager = manager
                            binding.rvExoPlayer.adapter = mPlayerAdapter
                        }
                    }
                }
            }
            intent.getStringExtra("TARGETED_FROM").toString()=="WorkoutDetails" -> {
                if (courseTodayWorkouts!=null)
                {
                    val mPlayerAdapter = ExoPlayerAdapter(this, dataList, courseTodayWorkouts!!.courseDurationExercise,mListner)
                    val manager = LinearLayoutManager(this)
                    manager.orientation = LinearLayoutManager.VERTICAL
                    binding.rvExoPlayer.layoutManager = manager
                    binding.rvExoPlayer.adapter = mPlayerAdapter
                }

            }
        }




    }

    private fun jsonStringToArrayListHashMap(jsonString: String): ArrayList<HashMap<String, String>> {
        val type = object : TypeToken<ArrayList<HashMap<String, String>>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    override fun onClickVideoInformation(position: Int, posData: CourseDurationExercise) {
        showVideoInformation(posData)
    }

    override fun onClickClosePlayer(position: Int) {
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun showVideoInformation(item: CourseDurationExercise) {

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


}