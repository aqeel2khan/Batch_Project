package com.example.bottomanimationmydemo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.BatchWorkoutTypeAdapter
import com.example.bottomanimationmydemo.adapter.WorkoutTypeListAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityCourseDetailBinding
import com.example.bottomanimationmydemo.meals.meal_unpurchase.view.activity.BuySubscriptionActivity
import com.example.bottomanimationmydemo.model.course_detail.CourseDuration
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.model.course_detail.WorkoutType
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

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
                /*.putExtra("course_id", course_id)*/
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
package com.example.bottomanimationmydemo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.BatchWorkoutTypeAdapter
import com.example.bottomanimationmydemo.adapter.WorkoutTypeListAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityCourseDetailBinding
import com.example.bottomanimationmydemo.model.course_detail.CourseDuration
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.model.course_detail.WorkoutType
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

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
