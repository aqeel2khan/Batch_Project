package com.dev.batchfinal.app_modules.shopping.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.BatchWorkoutTypeAdapter
import com.dev.batchfinal.adapter.WorkoutTypeListAdapter
import com.dev.batchfinal.app_custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.ActivityCourseDetailBinding
import com.dev.batchfinal.model.course_detail.CourseDuration
import com.dev.batchfinal.model.course_detail.Data
import com.dev.batchfinal.model.course_detail.WorkoutType
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class CourseDetailActivity : BaseActivity<ActivityCourseDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var sessionManager: UserSessionManager

    private var course_id: String? = null
    private var course_price: String? = null
    private lateinit var courseDetailData: Data
    val durationList: ArrayList<String> = ArrayList()
    val dataList: ArrayList<String> = ArrayList()
    val weekString = " days "
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        sessionManager= UserSessionManager(this)

        buttonClicks()
        startRelativeAnimation(binding.relWeightLayout)
        course_id = intent.getStringExtra("course_id")
        getCourseDetailData(course_id)
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
                                    setUpDetails(courseDetailData)
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

    @SuppressLint("SetTextI18n")
    private fun setUpDetails(courseData: Data) {
        //Check added by BBh
        try
        {
            course_price = courseData.coursePrice + "KWD"
            binding.courseName.text = courseData.courseName
            binding.courseDescription.text = courseData.description
            binding.coursePrice.text = course_price
            binding.coachName.text = courseData.coachDetail.name
            MyUtils.loadImage(binding.coachProfile, MyConstant.IMAGE_BASE_URL + courseData.coachDetail.profilePhotoPath)
            MyUtils.loadBackgroundImage(binding.backgroundImg, MyConstant.IMAGE_BASE_URL + courseData.courseImage)
            binding.trainingDay.text = "${courseData.perDayWorkout} trainings"
            binding.validateMin.text = courseData.duration + " min"
            binding.levelType.text = courseData.courseLevel.levelName
            binding.dollerText2.text = courseData.coursePrice + "KWD"
            if (courseData.workoutType.isNotEmpty()) {
                setWorkoutType(courseData.workoutType as List<WorkoutType>)
            }
            //  setWorkoutType(courseData.workoutType as List<WorkoutType>)
            if (courseData.courseDuration.isNotEmpty()) {
                setWorkoutTypeAdapter(courseData.courseDuration as List<CourseDuration>)
            }

            durationList.add(courseData.duration)
            //Check added by BBh
            if(durationList.size>0)
            {
                for (item in durationList) {
                    if (item == "Select Duration"){
                        dataList.add(item)
                    }else{
                        val newItem = item + weekString
                        dataList.add(newItem)
                    }
                }
                setSpinnerData(dataList)

            }
        }catch (e:Exception){e.printStackTrace()}

    }

    private fun setSpinnerData(dataList: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner!!.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selectedValue = this@CourseDetailActivity.dataList[position]
                // Append the selected value to the string variable
//                Toast.makeText(this@BuySubscriptionActivity, selectedValue, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }

    private fun setWorkoutType(workoutType: List<WorkoutType>) {
        binding.workType.apply {
            layoutManager = LinearLayoutManager(
                this@CourseDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = WorkoutTypeListAdapter(this@CourseDetailActivity, workoutType)
        }
    }

    private fun buttonClicks() {
        binding.btnSubscribe.setOnClickListener {
            startActivity(
                Intent(this@CourseDetailActivity, BuySubscriptionActivity::class.java)
                .putExtra("course_id", course_id)
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

    private fun setWorkoutTypeAdapter(courseDuration: List<CourseDuration>) {
        binding.recyclerWorkoutType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter =
            BatchWorkoutTypeAdapter(this@CourseDetailActivity, courseDuration)
    }

    override fun getViewBinding() = ActivityCourseDetailBinding.inflate(layoutInflater)
}



/* //running code
package com.dev.batchfinal.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.BatchWorkoutTypeAdapter
import com.dev.batchfinal.adapter.WorkoutTypeListAdapter
import com.dev.batchfinal.custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.ActivityCourseDetailBinding
import com.dev.batchfinal.model.course_detail.CourseDuration
import com.dev.batchfinal.model.course_detail.Data
import com.dev.batchfinal.model.course_detail.WorkoutType
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.utils.CheckNetworkConnection
import com.dev.batchfinal.utils.MyConstant
import com.dev.batchfinal.utils.MyCustom
import com.dev.batchfinal.utils.MyUtils
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource

@AndroidEntryPoint
class CourseDetailActivity : BaseActivity<ActivityCourseDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private var course_id: String? = null
    private var course_price: String? = null
    private lateinit var courseDetailData: Data
    val durationList: ArrayList<String> = ArrayList()
    val dataList: ArrayList<String> = ArrayList()
    val weekString = " days "
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        startRelativeAnimation(binding.relWeightLayout)
        course_id = intent.getStringExtra("course_id")
        getCourseDetailData(course_id)
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
                                    setUpDetails(courseDetailData)
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

    @SuppressLint("SetTextI18n")
    private fun setUpDetails(courseData: Data) {
        if (courseData != null) {
            course_price = courseData.coursePrice + "KWD"
            binding.courseName.text = courseData.courseName
            binding.courseDescription.text = courseData.description
            binding.coursePrice.text = course_price
            binding.coachName.text = courseData.coachDetail.name
            MyUtils.loadImage(binding.coachProfile, MyConstant.IMAGE_BASE_URL + courseData.coachDetail.profilePhotoPath)
            MyUtils.loadBackgroundImage(binding.backgroundImg, MyConstant.IMAGE_BASE_URL + courseData.courseImage)
            binding.trainingDay.text = "${courseData.perDayWorkout} trainings"
            binding.validateMin.text = courseData.duration + " min"
            binding.levelType.text = courseData.courseLevel.levelName
            binding.dollerText2.text = courseData.coursePrice + "KWD"
            if (!courseData.workoutType.isNullOrEmpty()) {
                setWorkoutType(courseData.workoutType as List<WorkoutType>)
            }
            //  setWorkoutType(courseData.workoutType as List<WorkoutType>)
            if (!courseData.courseDuration.isNullOrEmpty()) {
                setWorkoutTypeAdapter(courseData.courseDuration as List<CourseDuration>)
            }

            durationList.add(courseData.duration)
            for (item in durationList) {
                if (item.equals("Select Duration")){
                    val newItem = item
                    dataList.add(newItem)
                }else{
                    val newItem = item + weekString
                    dataList.add(newItem)
                }

            }
            setSpinnerData(dataList)
        }
    }

    private fun setSpinnerData(dataList: ArrayList<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner!!.adapter = adapter

        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                val selectedValue = this@CourseDetailActivity.dataList[position]
                // Append the selected value to the string variable
//                Toast.makeText(this@BuySubscriptionActivity, selectedValue, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }

    private fun setWorkoutType(workoutType: List<WorkoutType>) {
        binding.workType.apply {
            layoutManager = LinearLayoutManager(
                this@CourseDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = WorkoutTypeListAdapter(this@CourseDetailActivity, workoutType)
        }
    }

    private fun buttonClicks() {
        binding.btnSubscribe.setOnClickListener {
            startActivity(
                Intent(
                    this@CourseDetailActivity,
                    BuySubscriptionActivity::class.java
                )
                */
/*.putExtra("course_id", course_id)*//*

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

    private fun setWorkoutTypeAdapter(courseDuration: List<CourseDuration>) {
        binding.recyclerWorkoutType.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerWorkoutType.adapter =
            BatchWorkoutTypeAdapter(this@CourseDetailActivity, courseDuration)
    }

    override fun getViewBinding() = ActivityCourseDetailBinding.inflate(layoutInflater)
}*/
