package com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.activity

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.batchfinal.MainActivity
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.MyItemRecyclerViewAdapter
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_custom.MyCustomEditText
import com.dev.batchfinal.app_modules.meals.meal_purchase.model.meal_plan_subscribe.MealSubscribedRequest
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.adapter.DeliveryArrivingAdapter
import com.dev.batchfinal.app_modules.meals.meal_unpurchase.model.delivery_arriving.DeliveryArrivingResponse
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyConstant.status
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.databinding.ActivityCheckoutBinding
import com.dev.batchfinal.databinding.BottomSheetBinding
import com.dev.batchfinal.`interface`.DeliveryArrivingListPosition
import com.dev.batchfinal.`interface`.OnListFragmentInteractionListener
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel

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
import com.myfatoorah.sdk.enums.MFAPILanguage
import com.myfatoorah.sdk.enums.MFCurrencyISO
import com.myfatoorah.sdk.views.MFResult
import com.myfatoorah.sdk.views.MFSDK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckoutBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var sessionManager: UserSessionManager

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
    var course_id: String? = null


    var screen: String? = null
    var product_id: String? = null
    var product_name: String? = null
    var product_price: String? = null
    var product_cal: String? = null
    var product_img: String? = null
    var product_count: String? = null
    var product_snack: String? = null
    var product_updated: String? = null

    var selectedDeliveryTimeValue: String? = null
    var selectedDeliveryArrivngValue: String? = null
    var selectedDeliveryDropValue: String? = null
    var selectedDeliveryTimeId: Int? = null
    var selectedDeliveryArrivingId: Int? = null
    var selectedDeliveryDropId: Int? = null
    val deliveryTimeList: ArrayList<String> = ArrayList()

    var address_area: String? = null
    var address_block: String? = null
    var address_house: String? = null
    var address_street: String? = null
    var address_type: String? = null


    val request = MFInitiatePaymentRequest(0.100, MFCurrencyISO.KUWAIT_KWD)
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        sessionManager= UserSessionManager(this)

        screen = intent.getStringExtra("screen")
        product_id = intent.getStringExtra("product_id")

        buttonClicks()

        if (screen=="meal_batch") {
            getMealDetails(product_id)
        } else if (screen=="workout_batch"){
            getCourseDetailData(product_id)
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
                                    binding.rlMealBatch.visibility = View.GONE
                                    binding.rlDeliveryFee.visibility = View.GONE
                                    binding.cardWorkout.visibility = View.VISIBLE
                                    meal_price=response.data.coursePrice
                                    binding.courseName.text = response.data.courseName
                                    binding.coursePrice.text = "KD "+response.data.coursePrice.substringBefore(".")
                                    binding.tvSubtotalValue.text = "KD "+response.data.coursePrice.substringBefore(".")
                                    binding.tvTotalValue.text = "KD "+response.data.coursePrice.substringBefore(".")
                                    binding.coachName.text = response.data.coachDetail.name

                                    product_id=response.data.courseId.toString()
                                    product_name=response.data.courseName.toString()
                                    product_price=response.data.coursePrice.toString()
                                    product_count="5"
                                    product_updated=response.data.updatedAt.toString()

                                    MyUtils.loadImage(
                                        binding.coachProfile,
                                        MyConstant.IMAGE_BASE_URL + response.data.coachDetail.profilePhotoPath
                                    )
                                    MyUtils.loadBackgroundImage(
                                        binding.backgroundBg,
                                        MyConstant.IMAGE_BASE_URL + response.data.courseImage
                                    )
                                    binding.tvDuration.text = response.data.duration + " mins"
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

    private fun getMealDetails(meal_id: String?) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealDetailApiCall(meal_id!!)
            authViewModel.mealDetailResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealDetailResponse.removeObservers(this)
                        if (authViewModel.mealDetailResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
//                                    sharedPreferences.saveCourseId(response.data.courseId.toString())
                                    binding.relMealPlan.visibility = View.VISIBLE
                                    binding.rlDeliveryFee.visibility = View.VISIBLE
                                    binding.cardWorkout.visibility = View.GONE

                                    binding.tvMealPlan.text = response.data.data.name
                                    binding.mealPrice.text = "$"+response.data.data.price
                                    binding.mealCal.text = response.data.data.avgCalPerDay + " "
                                    binding.mealCount.text = response.data.data.mealCount.toString() + " "
                                    binding.mealSnack.text = response.data.data.snack_count.toString() + " "
                                    binding.tvSubtotalValue.text = "$"+response.data.data.price
                                    binding.tvTotalValue.text = "$"+response.data.data.price
                                    binding.coachName.text = response.data.data.name

                                    product_id=meal_id
                                    product_name=response.data.data.name.toString()
                                    product_price=response.data.data.price.toString()
                                    product_count=response.data.data.duration.toString()
                                    product_updated="29-02-2024"



                                    MyUtils.loadImage(
                                        binding.coachProfile,
                                        MyConstant.IMAGE_BASE_URL + response.data.data.meal_image
                                    )
                                    MyUtils.loadBackgroundImage(
                                        binding.backgroundBg,
                                        MyConstant.IMAGE_BASE_URL + response.data.data.meal_image
                                    )
                                    // binding.tvDuration.text = courseData.duration + " mins"
                                                              }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealDetailResponse.removeObservers(this)
                        if (authViewModel.mealDetailResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun buttonClicks() {
        binding.llPaymentMethod.setOnClickListener {
           // showPaymentMethodDialog()
        }
        binding.btnCheckout.setOnClickListener {
            showPaymentMethodDialog()




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
        binding.imgBack.setOnClickListener {
            finish()
        }
    }



    private fun validation() {
        var sub_total = binding.tvSubtotalValue.text.trim().toString()
        var total = binding.tvTotalValue.text.trim().toString()

    }


    private fun showDeliveryTime(type: String) {
        dialogBinding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(dialogBinding.root)
        if (type.equals("time")) {
            dialogBinding.spinnerSelectDeliveryTime!!.visibility = View.VISIBLE

            getDeliveryTime(dialogBinding.spinnerSelectDeliveryTime)

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
            getDeliveryArriving(dialogBinding.recyclerviewDeliveryArriving)
            dialogBinding.btnDateApply!!.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        } else {
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_delivery_arriving)
            dialogBinding.llDropOff.visibility = View.VISIBLE
            dialogBinding.llSelectDatePlan!!.visibility = View.VISIBLE
            dialogBinding.llSelectCalender.visibility = View.GONE
            getDeliveryDrop(dialogBinding.recyclerviewDeliveryDrop)

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
        val ll_bottom_change_course = dialog.findViewById<LinearLayout>(R.id.ll_bottom_change_course)
        val ll_select_date_plan = dialog.findViewById<LinearLayout>(R.id.ll_select_date_plan)
        val ll_select_calender = dialog.findViewById<LinearLayout>(R.id.ll_select_calender)
        val btn_date_apply = dialog.findViewById<Button>(R.id.btn_date_apply)
        val ll_map = dialog.findViewById<LinearLayout>(R.id.ll_map)
        val txt_title = dialog.findViewById<TextView>(R.id.txt_title)
        val btn_home = dialog.findViewById<CardView>(R.id.card_home)
        val btn_work = dialog.findViewById<CardView>(R.id.card_work)

        btn_home!!.setOnClickListener {
            btn_work!!.setCardBackgroundColor(Color.parseColor("#F5F6F4"))
            btn_home.setCardBackgroundColor(Color.parseColor("#F1E6DA"))
            address_type="Home"
        }
        btn_work!!.setOnClickListener {
            btn_work.setCardBackgroundColor(Color.parseColor("#F1E6DA"))
            btn_home.setCardBackgroundColor(Color.parseColor("#F5F6F4"))
            address_type="Work"

        }
        txt_title!!.text = resources.getString(R.string.txt_add_address)
        ll_bottom_change_course!!.visibility = View.GONE
        ll_select_calender!!.visibility = View.GONE
        ll_select_date_plan!!.visibility = View.VISIBLE
        ll_map!!.visibility = View.VISIBLE
        btn_date_apply!!.setOnClickListener {
            //code for save week price
            val area = dialog.findViewById<MyCustomEditText>(R.id.edit_area)
            val block = dialog.findViewById<MyCustomEditText>(R.id.edit_block)
            val house = dialog.findViewById<MyCustomEditText>(R.id.edit_house)
            val street = dialog.findViewById<MyCustomEditText>(R.id.edit_street)

            address_area= area!!.text.toString()
            address_block= block!!.text.toString()
            address_house= house!!.text.toString()
            address_street= street!!.text.toString()
            binding.txtAddress.visibility=View.VISIBLE
            binding.txtAddress.text=address_area+" "+address_block+" "+address_house+" "+address_street

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
            Date = (dayOfMonth.toString() + "-" + (month + 1) + "-" + year)

            // set this date in TextView for Display
            Log.d("date", Date!!)
            binding.txtSelectedDate.text=Date
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
        dialogBinding.imgDown3.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnApply.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }


        dialog.show()
    }

    private fun initiatePayment(dialogBinding: BottomSheetBinding, dialog: BottomSheetDialog) {
        binding.pbLoading.visibility = View.VISIBLE

//        val invoiceAmount = etAmount.text.toString().toDouble()
        val request = MFInitiatePaymentRequest(product_price!!.toDouble(), MFCurrencyISO.KUWAIT_KWD)

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
        val request = MFExecutePaymentRequest(paymentMethod, product_price!!.toDouble())

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
                    if (screen.equals("meal_batch")) {
                        mealSubscribe(result.response.invoiceTransactions!!.get(0).paymentGateway,
                            result.response.invoiceTransactions!!.get(0).transactionId,
                            result.response.invoiceTransactions!!.get(0).transactionStatus,
                            product_updated.toString(),"5")

                        showAlertDialog("Payment done successfully")
                    } else if (screen.equals("workout_batch")){
                        courseOrderCreate(result.response.invoiceTransactions!!.get(0).paymentGateway,
                            result.response.invoiceTransactions!!.get(0).transactionId,
                            result.response.invoiceTransactions!!.get(0).transactionStatus,
                            product_updated.toString(),"5")

                        showAlertDialog("Payment done successfully")

                    }else{

                    }



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

    private fun getDeliveryTime(sp_plan_duration: Spinner?) {
        deliveryTimeList.clear()
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.deliveryTimeApiCall()
            authViewModel.deliveryTimeResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.deliveryTimeResponse.removeObservers(this)
                        if (authViewModel.deliveryTimeResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                // showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    for (item in response.data.internaldata) {
                                        deliveryTimeList.add(item.timeSlot)



                                    }
                                    selectPlanDurationAdapter(deliveryTimeList, sp_plan_duration)


                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.deliveryTimeResponse.removeObservers(this)
                        if (authViewModel.deliveryTimeResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun selectPlanDurationAdapter(dataList: ArrayList<String>, sp_plan_duration: Spinner?) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dataList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_plan_duration!!.adapter = adapter

        sp_plan_duration.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                selectedDeliveryTimeValue = dataList[position]
                binding.txtSelectedDeliveryTime.visibility=View.VISIBLE
                binding.txtSelectedDeliveryTime.text=selectedDeliveryTimeValue
                selectedDeliveryTimeId=position+1
                // Append the selected value to the string variable
//                Toast.makeText(this@BuySubscriptionActivity, selectedValue, Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // Do nothing here
            }
        }
    }
    private fun getDeliveryArriving(delivery_recycler: RecyclerView) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.deliveryArrivingApiCall()
            authViewModel.deliveryArrivingResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.deliveryArrivingResponse.removeObservers(this)
                        if (authViewModel.deliveryArrivingResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                // showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    setupDeliveryArrivingAdapter(response.data.internaldata,delivery_recycler);

                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.deliveryArrivingResponse.removeObservers(this)
                        if (authViewModel.deliveryArrivingResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setupDeliveryArrivingAdapter(delivery_list:List<DeliveryArrivingResponse.Internaldatum>,delivery_recycler: RecyclerView) {
        delivery_recycler.apply {
            layoutManager = LinearLayoutManager(this@CheckOutActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DeliveryArrivingAdapter(this@CheckOutActivity,delivery_list,
                object :
                    DeliveryArrivingListPosition<Int> {
                    override fun onCategoryListItemPosition(
                        item: List<DeliveryArrivingResponse.Internaldatum>,
                        position: Int
                    ) {
                        selectedDeliveryArrivngValue=item[position].options
                        binding.txtDeliveryArrivingTime.visibility=View.VISIBLE
                        binding.txtDeliveryArrivingTime.text=selectedDeliveryArrivngValue

                        selectedDeliveryArrivingId=item[position].id.toInt()

                    }
                }                )
        }
    }
    private fun setupDeliveryDropAdapter(delivery_list:List<DeliveryArrivingResponse.Internaldatum>,delivery_recycler: RecyclerView) {
        delivery_recycler.apply {
            layoutManager = LinearLayoutManager(this@CheckOutActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DeliveryArrivingAdapter(this@CheckOutActivity,delivery_list,
                object :
                    DeliveryArrivingListPosition<Int> {
                    override fun onCategoryListItemPosition(
                        item: List<DeliveryArrivingResponse.Internaldatum>,
                        position: Int
                    ) {
                        selectedDeliveryArrivngValue=item[position].options
                        binding.txtDropOff.visibility=View.VISIBLE
                        binding.txtDropOff.text=selectedDeliveryArrivngValue

                        selectedDeliveryArrivingId=item[position].id.toInt()

                    }
                }                )
        }
    }

    private fun getDeliveryDrop(delivery_recycler: RecyclerView) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.deliveryDropApiCall()
            authViewModel.deliveryDropOffResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.deliveryDropOffResponse.removeObservers(this)
                        if (authViewModel.deliveryDropOffResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                // showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    setupDeliveryDropAdapter(response.data.internaldata,delivery_recycler);


                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.deliveryDropOffResponse.removeObservers(this)
                        if (authViewModel.deliveryDropOffResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }



    private fun courseOrderCreate(paymentType: String,transactionId: String,paymentStatus: String,startDate: String,duration: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty("course_id", product_id)
        jsonObject.addProperty("subtotal", product_price)
//        MyConstant.jsonObject.addProperty("subtotal", sub_total.toDouble())
        jsonObject.addProperty("discount", 0)
        jsonObject.addProperty("total", product_price)
        jsonObject.addProperty("payment_type", paymentType)
        jsonObject.addProperty("transaction_id", transactionId)
        jsonObject.addProperty("payment_status", paymentStatus)
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.courseOrderCreateApiCall("Bearer " + sessionManager.getUserToken(), jsonObject)
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
                                        Intent(this@CheckOutActivity, OrderCompleteActivity::class.java
                                        ).putExtra("message", response.message)
                                            .putExtra("meal_name", product_name)
                                            .putExtra("meal_price", product_price)
                                            .putExtra("meal_cal", "5")
                                            .putExtra("meal_img", product_img)
                                            .putExtra("meal_count", "")
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

    private fun mealSubscribe(paymentType: String,transactionId: String,paymentStatus: String,startDate: String,duration: String) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()

            val mealSubscribedRequest = MealSubscribedRequest()
            mealSubscribedRequest.userId=sessionManager.getUserId()
            mealSubscribedRequest.mealId=product_id
            mealSubscribedRequest.subtotal=product_price
            mealSubscribedRequest.total=product_price
            mealSubscribedRequest.discount="0"
            mealSubscribedRequest.tax="0"
            mealSubscribedRequest.paymentType=paymentType
            mealSubscribedRequest.transactionId=transactionId
            mealSubscribedRequest.paymentStatus=paymentStatus
            mealSubscribedRequest.startDate=startDate
            mealSubscribedRequest.duration=duration

            val jsonObject = JsonObject()
            jsonObject.addProperty("user_id", sessionManager.getUserId())
            jsonObject.addProperty("meal_id", product_id)
            jsonObject.addProperty("subtotal", product_price)
            jsonObject.addProperty("discount", "0")
            jsonObject.addProperty("tax", "0")
            jsonObject.addProperty("total", product_price)
            jsonObject.addProperty("payment_type", paymentType)
            jsonObject.addProperty("transaction_id", transactionId)
            jsonObject.addProperty("payment_status", paymentStatus)
            jsonObject.addProperty("start_date", Date)
            jsonObject.addProperty("duration", duration)
            jsonObject.addProperty("latitude", "28.421619")
            jsonObject.addProperty("longitude", "76.748520")
            jsonObject.addProperty("area", address_area)
            jsonObject.addProperty("block", address_block)
            jsonObject.addProperty("house", address_house)
            jsonObject.addProperty("street", address_street)
            jsonObject.addProperty("address_type", address_type)
            jsonObject.addProperty("delivery_time", selectedDeliveryTimeValue)
            jsonObject.addProperty("delivery_arriving", selectedDeliveryArrivngValue)
            jsonObject.addProperty("delivery_dropoff", selectedDeliveryArrivngValue)
            jsonObject.addProperty("delivery_time_id", selectedDeliveryTimeId)
            jsonObject.addProperty("delivery_arriving_id", selectedDeliveryArrivingId)
            jsonObject.addProperty("delivery_dropoff_id", selectedDeliveryArrivingId)



            authViewModel.mealSubscribeApiCall(jsonObject)
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
                                            .putExtra("meal_name", product_name)
                                            .putExtra("meal_price", product_price)
                                            .putExtra("meal_cal", product_cal)
                                            .putExtra("meal_img", product_img)
                                            .putExtra("meal_count", "")

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