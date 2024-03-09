package com.dev.batchfinal.app_modules.activity

import android.content.Intent
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.ChosenMealListAdapter
import com.dev.batchfinal.adapter.WeekdaysAdapter
import com.dev.batchfinal.databinding.FragmentCurrentMealDetailBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.model.meal_subscription_details_model.MealSubscriptionDetailsRequest
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrentMealDetailActivity : BaseActivity<FragmentCurrentMealDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()

    var calendar: Calendar = Calendar.getInstance()
    var date_filter: String? = null
    var str: String? = null
     var days_dishes: JSONObject?=null;
    var mealName = ArrayList(
        Arrays.asList("Breakfast", "Lunch", "Dinner"))
    private var meal_id: String? = null
    private var subscribe_id: String? = null



    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        meal_id = intent.getStringExtra("meal_id")
        subscribe_id = intent.getStringExtra("subscribe_id")
        getMealSubscribeDetailsApi(meal_id!!,subscribe_id!!)
        str = intent.getStringExtra("next")
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        date_filter = sdf.format(Date())
        startRelativeAnimation(binding.relWeightLayout)


        if (str.equals("Next")){
            //binding.llChosenMealNote.visibility = View.GONE
           // binding.recyclerChosenMealList.visibility = View.VISIBLE
            //setupChosenMealList()
        }else{
           // binding.llChosenMealNote.visibility = View.VISIBLE
           // binding.recyclerChosenMealList.visibility = View.GONE
        }
    }

    private fun getMealSubscribeDetailsApi(meal_id:String,subscription_id:String) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            val mealSubscriptionDetailsRequest = MealSubscriptionDetailsRequest()
            mealSubscriptionDetailsRequest.userId=sharedPreferences.userId
            mealSubscriptionDetailsRequest.mealId=meal_id
            mealSubscriptionDetailsRequest.subscribedId=subscription_id
            authViewModel.mealSubscribeDetailsApiCall(mealSubscriptionDetailsRequest)
            Log.d("Token","Bearer " + sharedPreferences.token);
            authViewModel.mealSubscriptionDetailsResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealSubscriptionDetailsResponse.removeObservers(this)
                        if (authViewModel.mealSubscriptionDetailsResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                var jsonObject = JSONObject(response!!.toString())
                                var status=jsonObject.getString("status")
                                if (status == "true") {
                                    var data = jsonObject.getJSONObject("data")
                                    var internaldata = data.getJSONObject("data")
                                    var meal_details = internaldata.getJSONObject("meal_details")
                                    binding.weightLossText.text=meal_details.getString("name")
                                    binding.messageText.text=meal_details.getString("description")
                                    binding.tvKcl.text=meal_details.getString("avg_cal_per_day")+"kcl"
                                    binding.tvMealCount.text=meal_details.getString("meal_count").toString()+"Meal"

                                    var subscribe_detail = internaldata.getJSONObject("subscribe_detail")

                                    val start_date_parts: List<String> = subscribe_detail.getString("start_date").split(" ")
                                    val start_date = start_date_parts[0]
                                    val end_date_parts: List<String> = subscribe_detail.getString("end_date").split(" ")
                                    val end_date = end_date_parts[0]
                                    val suspended_date = SimpleDateFormat("yyyy-MM-dd").parse(end_date)
                                    val suspend_month = DateFormat.format("MMM, d", suspended_date) as String // Thursday
                                    binding.grantText.text="On "+suspend_month+" the plan will be suspend"
                                    getWeekDays(start_date,end_date)

                                     days_dishes = subscribe_detail.getJSONObject("days_dishes")

                                    // val songs: JSONObject = jsonObject.getJSONObject("days_dishes")
                                    /* binding.tvDuration.text=response.data.internalData.subscribeDetail.selectedDuration.toString()
                                     binding.tvExpiration.text=response.data.internalData.subscribeDetail.endDate.toString()*/

                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        hideLoader()
                    }
                    is Resource.Failure -> {
                        authViewModel.mealSubscriptionDetailsResponse.removeObservers(this)
                        if (authViewModel.mealSubscriptionDetailsResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }


    private fun setupChosenMealList(days_dish:ArrayList<JSONObject>) {
        binding.recyclerChosenMealList.apply {
            layoutManager = LinearLayoutManager(this@CurrentMealDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ChosenMealListAdapter(this@CurrentMealDetailActivity, days_dish, object : PositionItemClickListener<Int>{
                override fun onPositionItemSelected(item: String, postions: Int) {
                    startActivity(Intent(this@CurrentMealDetailActivity, ChosenMealDetailActivity::class.java))
                }

            })
        }
    }
    private fun getDates(dateString1: String, dateString2: String): List<Date>? {
        val dates = ArrayList<Date>()
        val df1 = SimpleDateFormat("yyyy-MM-dd")
        var date1: Date? = null
        var date2: Date? = null
        try {
            date1 = df1.parse(dateString1)
            date2 = df1.parse(dateString2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val cal1 = Calendar.getInstance()
        cal1.time = date1
        val cal2 = Calendar.getInstance()
        cal2.time = date2
        while (!cal1.after(cal2)) {
            dates.add(cal1.time)
            cal1.add(Calendar.DATE, 1)
        }
        return dates
    }
    private fun getWeekDays(start_date:String,end_date:String) {
        val format = SimpleDateFormat("yyyy-MM-dd")
        calendar!!.firstDayOfWeek = Calendar.MONDAY
        calendar!!.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val datess = getDates(start_date, end_date)
        val dates = ArrayList<String?>()
        val days = ArrayList<String?>()
        for (j in datess!!) {
            System.out.println(format.format(j))
            dates.add(format.format(j))
            val dayOfTheWeek = DateFormat.format("EEE", j) as String // Thursday
            days.add(dayOfTheWeek)
            calendar!!.add(Calendar.DAY_OF_MONTH, 1)
        }
        for (i in dates) {
            if (date_filter == i) {
                setWeekdayAdapter(dates, days, "")
            }
        }
        setWeekdayAdapter(dates, days, "")



       /* for (i in 0..datess.size-1) {
            dates[i] = format.format(calendar!!.getTime())

            val dayOfTheWeek = DateFormat.format("EEE", calendar!!.time) as String // Thursday
            days[i] = dayOfTheWeek
            calendar!!.add(Calendar.DAY_OF_MONTH, 1)
        }*/
        //current date selection code

    }




    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
           onBackPressed()
        }
        binding.mealCalender.setOnClickListener {
            startActivity(Intent(this@CurrentMealDetailActivity, MealPlanActivity::class.java))
        }

    }

    private fun setWeekdayAdapter(dates: ArrayList<String?>, days: ArrayList<String?>, backDate: String?) {
        val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date: Date = inputFormat.parse(date_filter)
        val suspend_month = DateFormat.format("d", date) as String // Thursday
        setDish(suspend_month)
        binding.weekdaysList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.weekdaysList.adapter = WeekdaysAdapter(this,
            dates,
            days,
            backDate,
            "",
            date_filter,
            object : PositionItemClickListener<Int> {
                override fun onPositionItemSelected(
                    item: String, postions: Int
                ) {
                    date_filter = item.toString()
                    val inputFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val outputFormat: java.text.DateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy")
                    val date: Date = inputFormat.parse(date_filter)
                    val outputDateStr: String = outputFormat.format(date)



                    val mSpannableString = SpannableString(outputDateStr)
                    mSpannableString.setSpan(UnderlineSpan(), 0, mSpannableString.length, 0)
//                    binding.currentDate.text = mSpannableString

                    val itFormat: java.text.DateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy")
                    val otFormat: java.text.DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val suspend_month = DateFormat.format("d", date) as String // Thursday
                    setDish(suspend_month)
                    val date2: Date = itFormat.parse(outputDateStr)
                    val otDateStr: String = otFormat.format(date2)

//                    binding.etCalendar.setText(otDateStr)
                }
            })
    }


     fun setDish(date:String){
         try {
             binding.llChosenMealNote.visibility = View.GONE

             var item = days_dishes!!.optJSONObject(date)
             if (item!=null){
             val jsonObject = JSONObject(item.toString())
             val dishArray = ArrayList<JSONObject>()
                 dishArray.clear()
             for (key in jsonObject.keys()) {
                 val dishObject = jsonObject.getJSONObject(key)
                 dishArray.add(dishObject)
             }
             setupChosenMealList(dishArray);
             for (dish in dishArray) {
                 println(dish.toString())
                 // showToast(dish.getString("dish_name"))
             }}
         }catch (e:Exception){
             binding.llChosenMealNote.visibility = View.VISIBLE
         }

     }

    override fun getViewBinding() = FragmentCurrentMealDetailBinding.inflate(layoutInflater)



    override fun onResume() {
//        handleHeader(false)
//        handleBottomBar(false)
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}