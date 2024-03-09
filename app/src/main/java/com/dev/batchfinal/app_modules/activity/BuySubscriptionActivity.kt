package com.dev.batchfinal.app_modules.activity

import android.annotation.SuppressLint
import android.content.Intent
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
import com.dev.batchfinal.R
import com.dev.batchfinal.databinding.ActivityBuySubscriptionBinding
import com.dev.batchfinal.model.StatusModel
import com.dev.batchfinal.model.course_detail.Data
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.account.view.LoginActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.app_session.UserSessionManager

@AndroidEntryPoint
class BuySubscriptionActivity : BaseActivity<ActivityBuySubscriptionBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var statusList = ArrayList<StatusModel>()
    var status_id: Int = 0
    var isStatusPending = false
    var planName: String? = null
    var grand_total: String? = null
    var selectedValue: String? = null
    val durationList: ArrayList<String> = ArrayList()
    val dataList: ArrayList<String> = ArrayList()
    val weekString = " Weeks "
    private lateinit var sessionManager: UserSessionManager

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        sessionManager = UserSessionManager(this@BuySubscriptionActivity)
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
        binding.coursePrice.text = courseData.coursePrice + "KWD"
        binding.coachName.text = courseData.coachDetail.name
        MyUtils.loadImage(binding.coachProfile, MyConstant.IMAGE_BASE_URL + courseData.coachDetail.profilePhotoPath)
        MyUtils.loadBackgroundImage(binding.backgroundBg, MyConstant.IMAGE_BASE_URL + courseData.courseImage)
        binding.tvDuration.text = courseData.duration + " mins"
        binding.dollerText2.text = courseData.coursePrice + "KWD"
        if (!courseData.duration.isNullOrEmpty()){
            durationList.add(courseData.duration)
        }
        for (item in durationList) {
            if (item.equals("Select Duration")){
                val newItem = item
                dataList.add(newItem)
            }else{
                val newItem = item + weekString
                dataList.add(newItem)
            }

        }
    }

    private fun buttonClicks() {
        binding.llSelectPlan.setOnClickListener {
            showBottomSheetDialog()
        }
        binding.btnCheckout.setOnClickListener {
            if (binding.setPlanData.text.toString().isNullOrEmpty()){
                showToast("Please select plan duration")
            }else{
                if (sessionManager.isloggin()){

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
        selectPlanDurationAdapter(dataList, sp_plan_duration, textPlanDuration)
        ll_plan_duration!!.setOnClickListener {
            selectPlanDurationAdapter(dataList, sp_plan_duration, textPlanDuration)
        }
        btn_save!!.setOnClickListener {
            //code for save week price
            binding.setPlanData.visibility = View.VISIBLE
            binding.setPlanData.text = selectedValue
            dialog.dismiss()
        }
        img_down!!.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun selectPlanDurationAdapter(dataList: ArrayList<String>, sp_plan_duration: Spinner?, textPlanDuration: TextView?) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_plan_duration!!.adapter = adapter

        sp_plan_duration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                selectedValue = dataList[position]
                // Append the selected value to the string variable
//                Toast.makeText(this@BuySubscriptionActivity, selectedValue, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, CourseDetailActivity::class.java).putExtra("course_id", sharedPreferences.myCourseId))
    }

    override fun getViewBinding() = ActivityBuySubscriptionBinding.inflate(layoutInflater)
}