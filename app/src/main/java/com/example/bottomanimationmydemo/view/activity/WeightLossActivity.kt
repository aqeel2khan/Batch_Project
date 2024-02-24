package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.WorkoutTypeAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityWeightLossBinding
import com.example.bottomanimationmydemo.`interface`.PositionCourseWorkoutClick
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.model.courseorderlist.Course_duration
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import java.util.*

@AndroidEntryPoint
class WeightLossActivity : BaseActivity<ActivityWeightLossBinding>() {
    private lateinit var courseData: OrderList
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
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
        MyUtils.loadImage(
            binding.profileImage,
            MyConstant.IMAGE_BASE_URL + courseData.course_detail.coach_detail.profile_photo_path
        )

        buttonClicks()
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.relWeightLayout.startAnimation(aniSlide)
        setWorkoutTypeAdapter()
    }

    private fun getCourseDetailData(course_id: String?) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.courseDetailApiCall(course_id!!)
            authViewModel.courseDetailResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.courseDetailResponse.removeObservers(this)
                        if (authViewModel.courseDetailResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    courseDetailData = response.data
                                    sharedPreferences.saveCourseId(response.data.courseId.toString())
//                                    setUpDetails(courseDetailData)
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.courseDetailResponse.removeObservers(this)
                        if (authViewModel.courseDetailResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }


    private fun setWorkoutTypeAdapter() {
        binding.recyclerWorkoutType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter = WorkoutTypeAdapter(
            this@WeightLossActivity,
            courseData.course_detail.course_duration,
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
                ).putExtra("course_data",gson.toJson(courseData)))
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