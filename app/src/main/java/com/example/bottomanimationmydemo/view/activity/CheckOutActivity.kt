package com.example.bottomanimationmydemo.view.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.ActivityCheckoutBinding
import com.example.bottomanimationmydemo.databinding.BottomSheetBinding
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.*
import com.example.bottomanimationmydemo.utils.MyConstant.status
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource


@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckoutBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var strValue: String? = null
    var Date: String? = null
    lateinit var calendarView: CalendarView
    lateinit var dialogBinding: BottomSheetBinding
//    var course_id: String? = null
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        strValue = intent.getStringExtra("screen")
//        course_id = intent.getStringExtra("course_id")
        buttonClicks()

        if (strValue.equals("BatchMeal")) {
            binding.relMealPlan.visibility = View.VISIBLE
            binding.rlDeliveryFee.visibility = View.VISIBLE
            binding.cardWorkout.visibility = View.GONE
        } else {
            binding.rlMealBatch.visibility = View.GONE
            binding.rlDeliveryFee.visibility = View.GONE
            binding.cardWorkout.visibility = View.VISIBLE
        }
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
        binding.tvSubtotalValue.text = MyConstant.DOLLER_SIGN + courseData.coursePrice
        binding.tvTotalValue.text = MyConstant.DOLLER_SIGN + courseData.coursePrice
        binding.coachName.text = courseData.coachDetail.name
        MyUtils.loadImage(
            binding.coachProfile,
            MyConstant.IMAGE_BASE_URL + courseData.coachDetail.profilePhotoPath
        )
        MyUtils.loadBackgroundImage(
            binding.backgroundBg,
            MyConstant.IMAGE_BASE_URL + courseData.courseImage
        )
        binding.tvDuration.text = courseData.duration + " mins"
    }

    private fun buttonClicks() {
        binding.llPaymentMethod.setOnClickListener {
            showPaymentMethodDialog()
        }
        binding.btnCheckout.setOnClickListener {
            validation()
        }
        binding.rlDatePlan.setOnClickListener {
            showSelectDatePlanDialog()
        }
        binding.rlAddAddress.setOnClickListener {
            showAddressDialog()
        }
        binding.rlDeliveryTime.setOnClickListener {
            showDeliveryTime("time")
        }
        binding.rlDeliveryArriving.setOnClickListener {
            showDeliveryTime("arriving")
        }
        binding.rlDropOff.setOnClickListener {
            showDeliveryTime("dropOff")
        }
    }

    private fun validation() {
        var sub_total = binding.tvSubtotalValue.text.trim().toString()
        var total = binding.tvTotalValue.text.trim().toString()
        MyConstant.jsonObject.addProperty("course_id", sharedPreferences.myCourseId)
        MyConstant.jsonObject.addProperty("subtotal", 50)
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
        MyConstant.jsonObject.addProperty("discount", 0)
        MyConstant.jsonObject.addProperty("total", 50)
        MyConstant.jsonObject.addProperty("payment_type", "master card")
        MyConstant.jsonObject.addProperty("transaction_id", 28)
        MyConstant.jsonObject.addProperty("payment_status", "completed")
        courseOrderCreate(MyConstant.jsonObject)
    }

    private fun courseOrderCreate(jsonObject: JsonObject) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.courseOrderCreateApiCall("Bearer " + sharedPreferences.token, jsonObject)
            authViewModel.courseOrderCreateResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.courseOrderCreateResponse.removeObservers(this)
                        if (authViewModel.courseOrderCreateResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == status) {
                                    showToast(response.message)
                                    startActivity(
                                        Intent(
                                            this@CheckOutActivity,
                                            OrderCompleteActivity::class.java
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
                        authViewModel.courseOrderCreateResponse.removeObservers(this)
                        if (authViewModel.courseOrderCreateResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun showDeliveryTime(type: String) {
        dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        if (type.equals("time")) {
            dialogBinding.txtTitle!!.text = resources.getString(R.string.txt_delivery_time)
            dialogBinding.llBottomChangeCourse!!.visibility = View.GONE
            dialogBinding.llSelectCalender.visibility = View.GONE
            dialogBinding.llSelectDatePlan!!.visibility = View.VISIBLE
            dialogBinding.rlEditDeliveryTime!!.visibility = View.VISIBLE

            dialogBinding.btnDateApply!!.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        } else if (type.equals("arriving")) {
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_delivery_arriving)
            dialogBinding.llSelectDatePlan!!.visibility = View.VISIBLE
            dialogBinding.llArrivingInfo.visibility = View.VISIBLE
            dialogBinding.llSelectCalender.visibility = View.GONE
            dialogBinding.llBottomChangeCourse!!.visibility = View.GONE
            dialogBinding.cardCall.setOnClickListener {
                dialogBinding.cardCall.setBackgroundResource(R.drawable.radius_shape)
                dialogBinding.cardRingBell!!.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardNotification!!.setBackgroundResource(R.drawable.rectangle_button_gry_search)
            }
            dialogBinding.cardRingBell!!.setOnClickListener {
                dialogBinding.cardCall.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardRingBell.setBackgroundResource(R.drawable.radius_shape)
                dialogBinding.cardNotification!!.setBackgroundResource(R.drawable.rectangle_button_gry_search)
            }
            dialogBinding.cardNotification!!.setOnClickListener {
                dialogBinding.cardCall.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardRingBell.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardNotification.setBackgroundResource(R.drawable.radius_shape)
            }
            dialogBinding.btnDateApply!!.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        } else {
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_delivery_arriving)
            dialogBinding.llDropOff.visibility = View.VISIBLE
            dialogBinding.llSelectDatePlan!!.visibility = View.VISIBLE
            dialogBinding.llSelectCalender.visibility = View.GONE
            dialogBinding.cardMeetDoor.setOnClickListener {
                dialogBinding.cardMeetDoor.setBackgroundResource(R.drawable.radius_shape)
                dialogBinding.cardOutSide.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardLeave.setBackgroundResource(R.drawable.rectangle_button_gry_search)
            }
            dialogBinding.cardOutSide.setOnClickListener {
                dialogBinding.cardMeetDoor.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardOutSide.setBackgroundResource(R.drawable.radius_shape)
                dialogBinding.cardLeave.setBackgroundResource(R.drawable.rectangle_button_gry_search)
            }
            dialogBinding.cardLeave.setOnClickListener {
                dialogBinding.cardMeetDoor.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardOutSide.setBackgroundResource(R.drawable.rectangle_button_gry_search)
                dialogBinding.cardLeave.setBackgroundResource(R.drawable.radius_shape)
            }
            dialogBinding.btnDateApply!!.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        }


        dialog.show()
    }

    private fun showAddressDialog() {
        //add address
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course =
            dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_select_date_plan = dialog.findViewById<LinearLayout>(R.id.ll_select_date_plan)
        val ll_select_calender = dialog.findViewById<LinearLayout>(R.id.ll_select_calender)
        val btn_date_apply = dialog.findViewById<Button>(R.id.btn_date_apply)
        val ll_map = dialog.findViewById<LinearLayout>(R.id.ll_map)
        val txt_title = dialog.findViewById<TextView>(R.id.txt_title)
        txt_title!!.text = resources.getString(R.string.txt_add_address)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_select_calender!!.visibility = View.GONE
        ll_select_date_plan!!.visibility = View.VISIBLE
        ll_map!!.visibility = View.VISIBLE
        btn_date_apply!!.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showSelectDatePlanDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        val ll_bottom_change_course =
            dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_select_date_plan = dialog.findViewById<LinearLayout>(R.id.ll_select_date_plan)
        val ll_select_calender = dialog.findViewById<LinearLayout>(R.id.ll_select_calender)
        val btn_date_apply = dialog.findViewById<Button>(R.id.btn_date_apply)
        val edit_calender = dialog.findViewById<EditText>(R.id.edit_calender)
        val calendarView = dialog.findViewById<CalendarView>(R.id.calendarView)
        val ll_map = dialog.findViewById<LinearLayout>(R.id.ll_map)

        ll_bottom_change_course!!.visibility = View.GONE
        ll_map!!.visibility = View.GONE
        ll_select_calender!!.visibility = View.VISIBLE
        ll_select_date_plan!!.visibility = View.VISIBLE

        calendarView!!.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            // In this Listener we are getting values
            // such as year, month and day of month
            // on below line we are creating a variable
            // in which we are adding all the variables in it.
            Date = (dayOfMonth.toString() + "." + (month + 1) + "." + year)

            // set this date in TextView for Display
            Log.d("date", Date!!)
            edit_calender!!.setText(Date)
        })
        btn_date_apply!!.setOnClickListener {
            //code for save week price
            binding.txtSelectedDate.visibility = View.VISIBLE
            binding.txtSelectedDate.text = Date
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showPaymentMethodDialog() {
        dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)

        dialogBinding.llBottomChangeCourse.visibility = View.GONE
        dialogBinding.llPaymentMethod.visibility = View.VISIBLE
        dialogBinding.cardLayout.visibility = View.VISIBLE
        dialogBinding.llAddNewCard.setOnClickListener {
            addNewCardDialog()
            dialog.dismiss()
        }
        dialogBinding.btnApply.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addNewCardDialog() {
        dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.llBottomChangeCourse.visibility = View.GONE
        dialogBinding.llNewCard.visibility = View.VISIBLE
        dialogBinding.llPaymentMethod.visibility = View.GONE
        dialogBinding.imgDown7.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSaveCard.setOnClickListener {
            dialog.dismiss()

        }

        dialog.show()
    }

    override fun getViewBinding() = ActivityCheckoutBinding.inflate(layoutInflater)
}