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
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.bottomanimationmydemo.MainActivity
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.MyItemRecyclerViewAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityCheckoutBinding
import com.example.bottomanimationmydemo.databinding.BottomSheetBinding
import com.example.bottomanimationmydemo.`interface`.OnListFragmentInteractionListener
import com.example.bottomanimationmydemo.model.course_detail.Data
import com.example.bottomanimationmydemo.model.meal_plan_subscribe.MealSubscribedRequest
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.*
import com.example.bottomanimationmydemo.utils.MyConstant.status
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.myfatoorah.sdk.entity.executepayment.MFExecutePaymentRequest
import com.myfatoorah.sdk.entity.executepayment_cardinfo.MFCardInfo
import com.myfatoorah.sdk.entity.executepayment_cardinfo.MFDirectPaymentResponse
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentRequest
import com.myfatoorah.sdk.entity.initiatepayment.MFInitiatePaymentResponse
import com.myfatoorah.sdk.entity.initiatepayment.PaymentMethod
import com.myfatoorah.sdk.entity.initiatesession.MFInitiateSessionRequest
import com.myfatoorah.sdk.entity.paymentstatus.MFGetPaymentStatusResponse
import com.myfatoorah.sdk.entity.sendpayment.MFSendPaymentRequest
import com.myfatoorah.sdk.entity.sendpayment.MFSendPaymentResponse
import com.myfatoorah.sdk.enums.MFAPILanguage
import com.myfatoorah.sdk.enums.MFCurrencyISO
import com.myfatoorah.sdk.enums.MFNotificationOption
import com.myfatoorah.sdk.views.MFResult
import com.myfatoorah.sdk.views.MFSDK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource


@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckoutBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var strValue: String? = null
    var Date: String? = null
    private var meal_id: String? = null
    private var gole_id: String? = null
    private var meal_name: String? = null
    private var meal_price: String? = null
    private var meal_cal: String? = null
    private var meal_img: String? = null
    private var meal_count: String? = null
    private var meal_snack: String? = null
    lateinit var dialogBinding: BottomSheetBinding
    private lateinit var adapter: MyItemRecyclerViewAdapter
    private var selectedPaymentMethod: PaymentMethod? = null

    val request = MFInitiatePaymentRequest(0.100, MFCurrencyISO.KUWAIT_KWD)
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        strValue = intent.getStringExtra("screen")
        meal_id = intent.getStringExtra("meal_id")
        meal_name = intent.getStringExtra("meal_name")
        meal_price = intent.getStringExtra("meal_price")
        meal_cal = intent.getStringExtra("meal_cal")
        meal_img = intent.getStringExtra("meal_img")
        meal_count = intent.getStringExtra("meal_count")
        meal_snack = intent.getStringExtra("meal_snack")
        buttonClicks()

        if (strValue.equals("BatchMeal")) {
            binding.relMealPlan.visibility = View.VISIBLE
            binding.rlDeliveryFee.visibility = View.VISIBLE
            binding.cardWorkout.visibility = View.GONE

            binding.tvMealPlan.text = meal_name
            binding.mealPrice.text = "$"+meal_price
            binding.mealCal.text = meal_cal + " "
            binding.mealCount.text = meal_count + " "
            binding.mealSnack.text = meal_snack + " "
            binding.tvSubtotalValue.text = "$"+meal_price
            binding.tvTotalValue.text = "$"+meal_price
            binding.coachName.text = meal_name
            MyUtils.loadImage(
                binding.coachProfile,
                MyConstant.IMAGE_BASE_URL + meal_img
            )
            MyUtils.loadBackgroundImage(
                binding.backgroundBg,
                MyConstant.IMAGE_BASE_URL + meal_img
            )
           // binding.tvDuration.text = courseData.duration + " mins"

        } else {
            binding.rlMealBatch.visibility = View.GONE
            binding.rlDeliveryFee.visibility = View.GONE
            binding.cardWorkout.visibility = View.VISIBLE
            getCourseDetailData(sharedPreferences.myCourseId)

        }
        initiateSession()
    }

    private fun initiateSession() {
        /**
         * If you want to use saved card option with embedded payment, send the parameter
         * "customerIdentifier" with a unique value for each customer. This value cannot be used
         * for more than one Customer.
         */
        val mfInitiateSessionRequest = MFInitiateSessionRequest(customerIdentifier = "12332212")
        MFSDK.initiateSession(request = null) {
            when (it) {
                is MFResult.Success -> {
                    binding.mfPaymentView.load(
                        it.response,
                        onCardBinChanged = { bin ->
                            Log.d("CheckoutFatoora", "bin: $bin")
                        }
                    )
                }
                is MFResult.Fail -> {
                    Log.d("CheckoutFatoora", "Fail: " + Gson().toJson(it.error))
                    showAlertDialog(Gson().toJson(it.error))
                }
                else -> {}
            }
        }
    }

    private fun showAlertDialog(text: String) {
        AlertDialog.Builder(this)
            .setMessage(text)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
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
        binding.tvSubtotalValue.text = courseData.coursePrice + "KWD"
        binding.tvTotalValue.text = courseData.coursePrice + "KWD"
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
           // showPaymentMethodDialog()
        }
        binding.btnCheckout.setOnClickListener {
            if (strValue.equals("BatchMeal")) {
                showPaymentMethodDialog()
            } else {
                validation()
            }


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
        initiatePayment(dialogBinding, dialog)
        /*dialogBinding.llAddNewCard.setOnClickListener {
            addNewCardDialog()
            dialog.dismiss()
        }*/
        dialogBinding.btnApply.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun initiatePayment(dialogBinding: BottomSheetBinding, dialog: BottomSheetDialog) {
        binding.pbLoading.visibility = View.VISIBLE

//        val invoiceAmount = etAmount.text.toString().toDouble()
        val request = MFInitiatePaymentRequest(meal_price!!.toDouble(), MFCurrencyISO.KUWAIT_KWD)

        MFSDK.initiatePayment(
            request,
            MFAPILanguage.EN
        ) { result: MFResult<MFInitiatePaymentResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d("CheckoutFatoora", "Response: " + Gson().toJson(result.response))
                    setAvailablePayments(result.response.paymentMethods!!, dialogBinding, dialog)

                }
                is MFResult.Fail -> {
                    Log.d("CheckoutFatoora", "Fail: " + Gson().toJson(result.error))
                }
                else -> {}
            }
            binding.pbLoading.visibility = View.GONE
        }
    }

    private fun setAvailablePayments(paymentMethods: ArrayList<PaymentMethod>, dialogBinding: BottomSheetBinding, dialog: BottomSheetDialog) {
        adapter = MyItemRecyclerViewAdapter(paymentMethods, object :
            OnListFragmentInteractionListener {
            override fun onListFragmentInteraction(position: Int, item: PaymentMethod) {
                selectedPaymentMethod = item

//                adapter.changeSelected(position)
//                paymentData(dialogBinding)

                if (item.directPayment)
                    dialogBinding.llDirectPaymentLayout.visibility = View.VISIBLE
                else
                    dialogBinding.llDirectPaymentLayout.visibility = View.GONE
                dialog.dismiss()
            }
        }, object : MyItemRecyclerViewAdapter.OnRadioButtonClickListener{
            override fun onRadioButtonClick(item: PaymentMethod) {
                selectedPaymentMethod = item
                Log.d("selectedCard", selectedPaymentMethod!!.paymentMethodEn)
                binding.selectedCard.text = selectedPaymentMethod!!.paymentMethodEn
                paymentData(dialogBinding)
                dialog.dismiss()
            }
        })
        dialogBinding.rvPaymentMethod.adapter = adapter
    }

    private fun paymentData(dialogBinding: BottomSheetBinding) {
        if (!selectedPaymentMethod?.directPayment!!)
            executePayment(selectedPaymentMethod?.paymentMethodId!!)
        else {
            when {
//                dialogBinding.etAmount.text.isEmpty() -> etCardNumber.error = "Required"
                dialogBinding.etCardNumber.text!!.isEmpty() -> dialogBinding.etCardNumber.error = "Required"
                dialogBinding.etExpiryMonth.text!!.isEmpty() -> dialogBinding.etExpiryMonth.error = "Required"
                dialogBinding.etExpiryYear.text!!.isEmpty() -> dialogBinding.etExpiryYear.error = "Required"
                dialogBinding.etSecurityCode.text!!.isEmpty() -> dialogBinding.etSecurityCode.error = "Required"
                dialogBinding.etCardHolderName.text!!.isEmpty() -> dialogBinding.etCardHolderName.error = "Required"
                else -> executePaymentWithCardInfo(selectedPaymentMethod?.paymentMethodId!!)
            }
        }
    }

    private fun executePayment(paymentMethod: Int) {

//        val invoiceAmount = etAmount.text.toString().toDouble()
        val request = MFExecutePaymentRequest(paymentMethod, meal_price!!.toDouble())

//        request.recurringModel = RecurringModel(MFRecurringType.DAILY, 5)

        MFSDK.executePayment(
            this,
            request,
            MFAPILanguage.EN,
            onInvoiceCreated = {
                Log.d("CheckoutFatoora", "invoiceId: $it")
            }
        ) { invoiceId: String, result: MFResult<MFGetPaymentStatusResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d("CheckoutFatoora", "Response: " + Gson().toJson(result.response))
                    mealSubscribe(result.response.invoiceTransactions!!.get(0).paymentGateway,
                        result.response.invoiceTransactions!!.get(0).transactionId,
                        result.response.invoiceTransactions!!.get(0).transactionStatus,
                        "29-02-2024","1")

                    showAlertDialog("Payment done successfully")
                }
                is MFResult.Fail -> {
                    Log.d("CheckoutFatoora", "Fail: " + Gson().toJson(result.error))
                    showAlertDialog("Payment failed")
                }
                else -> {}
            }

            dialogBinding.pbLoading.visibility = View.GONE
        }
    }

    private fun executePaymentWithCardInfo(paymentMethod: Int) {
        dialogBinding.pbLoading.visibility = View.VISIBLE

//        val invoiceAmount = etAmount.text.toString().toDouble()
        val request = MFExecutePaymentRequest(paymentMethod, 200.00)

//        val mfCardInfo = MFCardInfo("Your token here")
        val mfCardInfo = MFCardInfo(
            dialogBinding.etCardNumber.text.toString(),
            dialogBinding.etExpiryMonth.text.toString(),
            dialogBinding.etExpiryYear.text.toString(),
            dialogBinding.etSecurityCode.text.toString(),
            dialogBinding.etCardHolderName.text.toString(),
            false,
            false
        )

        MFSDK.executeDirectPayment(
            this,
            request,
            mfCardInfo,
            MFAPILanguage.EN,
            onInvoiceCreated = {
                Log.d("CheckoutFatoora", "invoiceId: $it")
            }
        ) { invoiceId: String, result: MFResult<MFDirectPaymentResponse> ->
            when (result) {
                is MFResult.Success -> {
                    Log.d("CheckoutFatoora", "Response: " + Gson().toJson(result.response))
                    showAlertDialog("Payment done successfully")
                }
                is MFResult.Fail -> {
                    Log.d("CheckoutFatoora", "Fail: " + Gson().toJson(result.error))
                    showAlertDialog("Payment failed")
                }
                else -> {}
            }

            dialogBinding.pbLoading.visibility = View.GONE
        }
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

    private fun mealSubscribe(paymentType: String,transactionId: String,paymentStatus: String,startDate: String,duration: String) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()

            val mealSubscribedRequest = MealSubscribedRequest()
            mealSubscribedRequest.userId=sharedPreferences.userId
            mealSubscribedRequest.mealId=meal_id
            mealSubscribedRequest.subtotal=meal_price
            mealSubscribedRequest.total=meal_price
            mealSubscribedRequest.discount="0"
            mealSubscribedRequest.tax="0"
            mealSubscribedRequest.paymentType=paymentType
            mealSubscribedRequest.transactionId=transactionId
            mealSubscribedRequest.paymentStatus=paymentStatus
            mealSubscribedRequest.startDate=startDate
            mealSubscribedRequest.duration=duration

            authViewModel.mealSubscribeApiCall(mealSubscribedRequest)
            authViewModel.mealsSubscribedRespnse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealsSubscribedRespnse.removeObservers(this)
                        if (authViewModel.mealsSubscribedRespnse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    showToast(response.status.toString())
                                    startActivity(
                                        Intent(
                                            this@CheckOutActivity,
                                            OrderCompleteActivity::class.java
                                        ).putExtra("message", response.message)
                                            .putExtra("meal_name", meal_name)
                                            .putExtra("meal_price", meal_price)
                                            .putExtra("meal_cal", meal_cal)
                                            .putExtra("meal_img", meal_img)
                                            .putExtra("meal_count", meal_count)

                                    )

                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealsSubscribedRespnse.removeObservers(this)
                        if (authViewModel.mealsSubscribedRespnse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun getViewBinding() = ActivityCheckoutBinding.inflate(layoutInflater)
}