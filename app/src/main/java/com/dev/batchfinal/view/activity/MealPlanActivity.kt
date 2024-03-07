package com.dev.batchfinal.view.activity

import android.content.Intent
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.UnderlineSpan
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.adapter.WeekdaysAdapter
import com.dev.batchfinal.databinding.ActivityMealPlanBinding
//import com.dev.batchfinal.databinding.ActivityMealPlanBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
//import com.example.bottomanimationmydemo.adapter.WeekdaysAdapter
//import com.example.bottomanimationmydemo.databinding.ActivityMealPlanBinding
//import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
//import com.example.bottomanimationmydemo.view.BaseActivity
//import com.example.bottomanimationmydemo.viewmodel.AllViewModel
//import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MealPlanActivity : BaseActivity<ActivityMealPlanBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var calendar: Calendar = Calendar.getInstance()
    var date_filter: String? = null
    var name = ArrayList(
        Arrays.asList("Breakfast", "Lunch & Dinner", "Snack", "Desserts" ))

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        date_filter = sdf.format(Date())
        startLinearLayoutAnimation(binding.llMainLayout)
        getWeekDays()
        setupMealPlanList()
        setUpAllMealsAdapter()
    }

    private fun setupMealPlanList() {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(this@MealPlanActivity, LinearLayoutManager.HORIZONTAL, false)
//            adapter = MealPlanListAdapter(this@MealPlanActivity, name, object : PositionItemClickListener<Int>{
//                override fun onPositionItemSelected(item: String, postions: Int) {
//                    setUpAllMealsAdapter()
//                }
//
//            })
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

    private fun getWeekDays() {
        val format = SimpleDateFormat("yyyy-MM-dd")
        calendar!!.firstDayOfWeek = Calendar.MONDAY
        calendar!!.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val dates = arrayOfNulls<String>(7)
        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            dates[i] = format.format(calendar!!.getTime())
            val dayOfTheWeek = DateFormat.format("EEE", calendar!!.time) as String // Thursday
            days[i] = dayOfTheWeek
            calendar!!.add(Calendar.DAY_OF_MONTH, 1)
        }
        //current date selection code
        for (i in dates) {
            if (date_filter == i) {
                //setWeekdayAdapter(dates, days, "", "mealPlan")
            }
        }

       // setWeekdayAdapter(dates, days, "", "mealPlan")
    }

/*
    private fun setWeekdayAdapter(dates: Array<String?>, days: Array<String?>, backDate: String?, mealPlan: String) {
        binding.weekdaysList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.weekdaysList.adapter = WeekdaysAdapter(this,
            dates,
            days,
            backDate,
            mealPlan,
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
//                    val otFormat: java.text.DateFormat = SimpleDateFormat("MMMM dd, yyyy")
                    val date2: Date = itFormat.parse(outputDateStr)
                    val otDateStr: String = otFormat.format(date2)
//                    binding.etCalendar.setText(otDateStr)
                }
            })
    }
*/

    override fun getViewBinding() = ActivityMealPlanBinding.inflate(layoutInflater)
}