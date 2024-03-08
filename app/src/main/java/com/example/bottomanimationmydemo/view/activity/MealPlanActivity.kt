package com.example.bottomanimationmydemo.view.activity

import android.content.Intent
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.UnderlineSpan
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.AllTypeOfMealAdapter
import com.example.bottomanimationmydemo.adapter.MealSubscriptionCategoryAdapter
import com.example.bottomanimationmydemo.adapter.WeekdaysAdapter
import com.example.bottomanimationmydemo.databinding.ActivityMealPlanBinding
import com.example.bottomanimationmydemo.`interface`.MealDishListItemPosition
import com.example.bottomanimationmydemo.`interface`.MealSubscriptionCategoryListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.meal_dish_model.MealDishData
import com.example.bottomanimationmydemo.model.meal_subscription_details_model.MealSubscriptionCategoryResponse
import com.example.bottomanimationmydemo.model.meal_subscription_details_model.MealSubscriptionDetailsRequest
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import org.json.JSONException
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MealPlanActivity : BaseActivity<ActivityMealPlanBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()

    var calendar: Calendar = Calendar.getInstance()
    var date_filter: String? = null
    var name = ArrayList(
        Arrays.asList("Breakfast", "Lunch & Dinner", "Snack", "Desserts" ))
    private var meal_id: String? = null
    private var subscribe_id: String? = null
    var days_dishes: JSONObject?=null;

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        meal_id = intent.getStringExtra("meal_id")
        subscribe_id = intent.getStringExtra("subscribe_id")
        getMealSubscribeDetailsApi(meal_id!!,subscribe_id!!)
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        date_filter = sdf.format(Date())
        startLinearLayoutAnimation(binding.llMainLayout)

        setUpAllMealsAdapter()
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
                                    var subscribe_detail = internaldata.getJSONObject("subscribe_detail")
                                    val start_date_parts: List<String> = subscribe_detail.getString("start_date").split(" ")
                                    val start_date = start_date_parts[0]
                                    val end_date_parts: List<String> = subscribe_detail.getString("end_date").split(" ")
                                    val end_date = end_date_parts[0]
                                    val suspended_date = SimpleDateFormat("yyyy-MM-dd").parse(end_date)
                                    val suspend_month = DateFormat.format("MMM, d", suspended_date) as String // Thursday
                                    getWeekDays(start_date,end_date)

                                    days_dishes = subscribe_detail.getJSONObject("days_dishes")
                                    try {
                                        var category_list = meal_details.getJSONArray("category_list")
                                        val mealSubscriptionCategoryResponse: MutableList<MealSubscriptionCategoryResponse> = ArrayList()
                                        for (i in 0 until category_list.length()) {
                                            val jsonObj: JSONObject = category_list.getJSONObject(i)
                                            val data = MealSubscriptionCategoryResponse()
                                            data.categoryId=jsonObj.getString("category_id")
                                            data.categoryName=jsonObj.getString("category_name")
                                            data.categoryType=jsonObj.getString("category_type")
                                            mealSubscriptionCategoryResponse.add(data)
                                        }
                                        setupMealPlanList(mealSubscriptionCategoryResponse)

                                    } catch (e: JSONException) {
                                        e.printStackTrace()
                                    }
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



    private fun setupMealPlanList(mealSubscriptionCategoryResponse: MutableList<MealSubscriptionCategoryResponse>) {
        getMealDish(1)

        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(this@MealPlanActivity, LinearLayoutManager.HORIZONTAL, false)
          adapter = MealSubscriptionCategoryAdapter(this@MealPlanActivity, mealSubscriptionCategoryResponse, object :
              MealSubscriptionCategoryListItemPosition<Int> {
              override fun onMealSubscriptionCategoryListItemPosition(
                  item: MutableList<MealSubscriptionCategoryResponse>,
                  position: Int
              ) {
                  getMealDish(item[position].categoryId.toInt())
                  //dish items
//                    setUpAllMealsAdapter()
              }
          })
        }
    }

    private fun getMealDish(categoryId: Int) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.mealDishApiCall(categoryId)
            authViewModel.mealDishResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealDishResponse.removeObservers(this)
                        if (authViewModel.mealDishResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    setUpAllMealsAdapter(response.data.data)
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mealDishResponse.removeObservers(this)
                        if (authViewModel.mealDishResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setUpAllMealsAdapter(mealdishList: List<MealDishData>) {
        binding.recyclerAllMeal.apply {
            layoutManager = GridLayoutManager(this@MealPlanActivity, 2)
            adapter = AllTypeOfMealAdapter(this@MealPlanActivity, mealdishList,"meal_plan",
                object : MealDishListItemPosition<Int> {
                    override fun onMealDishListItemPosition(item: MealDishData, position: Int) {
                        //redirect code here
                       /* val intent = Intent(this@MealPlanActivity, ChosenMealDetailActivity::class.java)
                        intent.putExtra("dish_id",item.dishId.toString())
                        intent.putExtra("meal_id",item.mealId.toString())
                        intent.putExtra("goal_id",gole_id)
                        startActivity(intent)*/
                    }
                })
        }
    }


    private fun setUpAllMealsAdapter() {
        binding.recyclerAllMeal.apply {
            layoutManager = GridLayoutManager(this@MealPlanActivity, 2)
//            adapter = AllTypeOfMealAdapter(this@MealPlanActivity, name,
//                object : PositionItemClickListener<Int> {
//                    override fun onPositionItemSelected(item: String, postions: Int) {
//                    startActivity(Intent(this, MotivatorDetailActivity::class.java))
//                    }
//                })
        }
    }

    private fun buttonClicks() {
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this@MealPlanActivity, CurrentMealDetailActivity::class.java).putExtra("next", "Next"))
        }
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
            "mealPlan",
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

            var item = days_dishes!!.optJSONObject(date)
            if (item!=null){
                val jsonObject = JSONObject(item.toString())
                val dishArray = ArrayList<JSONObject>()
                dishArray.clear()
                for (key in jsonObject.keys()) {
                    val dishObject = jsonObject.getJSONObject(key)
                    dishArray.add(dishObject)
                }
                //setupChosenMealList(dishArray);
                for (dish in dishArray) {
                    println(dish.toString())
                    // showToast(dish.getString("dish_name"))
                }}
        }catch (e:Exception){
        }

    }






    override fun getViewBinding() = ActivityMealPlanBinding.inflate(layoutInflater)
}