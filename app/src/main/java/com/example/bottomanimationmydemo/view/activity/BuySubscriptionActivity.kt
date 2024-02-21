package com.example.bottomanimationmydemo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityBuySubscriptionBinding
import com.example.bottomanimationmydemo.model.StatusModel
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class BuySubscriptionActivity : BaseActivity<ActivityBuySubscriptionBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var statusList = ArrayList<StatusModel>()
    var status_id: Int = 0
    var isStatusPending = false
    var planName: String? = null
    var grand_total: String? = null
//    var course_id: String? = null
    lateinit var myObject: Data
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
//        course_id = intent.getStringExtra("course_id")
        statusList = arrayListOf<StatusModel>(
            StatusModel(id = -1, status = "Select Plan Duration"),
            StatusModel(id = 0, status = "Weekly"),
            StatusModel(id = 1, status = "Monthly"),
        )
        getCourseDetailData(sharedPreferences.myCourseId)
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
                                    setUpDetails(response.data)
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
        binding.courseName.text = courseData.courseName
        binding.coursePrice.text = MyConstant.DOLLER_SIGN + courseData.coursePrice
        binding.coachName.text = courseData.coachDetail.name
        MyUtils.loadImage(binding.coachProfile, MyConstant.IMAGE_BASE_URL + courseData.coachDetail.profilePhotoPath)
        MyUtils.loadBackgroundImage(binding.backgroundBg, MyConstant.IMAGE_BASE_URL + courseData.courseImage)
        binding.tvDuration.text = courseData.duration + " mins"
        binding.dollerText2.text = MyConstant.DOLLER_SIGN + courseData.coursePrice
    }

    private fun buttonClicks() {
        binding.llSelectPlan.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.btnCheckout.setOnClickListener {
            if (binding.setPlanData.text.toString().isNullOrEmpty()){
                showToast("Please select plan duration")
            }else{
                if (sharedPreferences.token != "") {
                    startActivity(
                        Intent(this@BuySubscriptionActivity, CheckOutActivity::class.java).putExtra("course_id", sharedPreferences.myCourseId)
                    )
                } else {
                    startActivity(
                        Intent(this@BuySubscriptionActivity, LoginActivity::class.java)
                    )
                }
            }
        }
    }

    private fun showBottomSheetDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_save_plan_layout = dialog.findViewById<LinearLayout>(R.id.ll_save_plan_layout)
        val ll_plan_duration = dialog.findViewById<LinearLayout>(R.id.ll_plan_duration)
        val sp_plan_duration = dialog.findViewById<Spinner>(R.id.sp_plan_duration)
        val textPlanDuration = dialog.findViewById<TextView>(R.id.textPlanDuration)
        val btn_save = dialog.findViewById<Button>(R.id.btn_save)
        val img_down = dialog.findViewById<ImageView>(R.id.img_down)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_save_plan_layout!!.visibility = View.VISIBLE
        ll_plan_duration!!.setOnClickListener {
            selectPlanDurationAdapter(statusList, sp_plan_duration, textPlanDuration)
        }
        btn_save!!.setOnClickListener {
            //code for save week price
            binding.setPlanData.visibility = View.VISIBLE
            binding.setPlanData.text = planName
            dialog.dismiss()
        }
        img_down!!.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun selectPlanDurationAdapter(
        statusList: ArrayList<StatusModel>,
        sp_plan_duration: Spinner?,
        textPlanDuration: TextView?
    ) {
        sp_plan_duration!!.visibility = View.VISIBLE
        sp_plan_duration.performClick()

        val Information = ArrayList<String>()
        val informationData = statusList
        val indId = ArrayList<Int>()
//        Information.add("Select Status")
//        indId.add(0)
        for (items in informationData) {
            Information.add(items.status.toString())
            indId.add(items.id!!.toInt())
        }
        val activityAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Information)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_plan_duration.adapter = activityAdapter
        sp_plan_duration.post(
            Runnable {
                sp_plan_duration.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            val txtData = sp_plan_duration.selectedView as TextView
                            if (p2 != 0) {
                                isStatusPending = true
                                status_id = indId[p2]
                                Log.d("status_id", status_id.toString())
                                planName = Information[p2]
                                textPlanDuration!!.text = planName
                                sp_plan_duration.visibility = View.GONE
                                txtData.visibility = View.VISIBLE
                            } else {
                                sp_plan_duration.visibility = View.VISIBLE

                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            val txtData = sp_plan_duration.selectedView as TextView
                            txtData.visibility = View.VISIBLE
                        }
                    }
            }
        )
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CourseDetailActivity::class.java).putExtra("course_id", sharedPreferences.myCourseId))
    }

    override fun getViewBinding() = ActivityBuySubscriptionBinding.inflate(layoutInflater)
}