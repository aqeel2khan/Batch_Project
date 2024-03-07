package com.dev.batchfinal.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.UnderlineSpan
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.adapter.ChosenMealListAdapter
import com.dev.batchfinal.adapter.WeekdaysAdapter
import com.dev.batchfinal.databinding.FragmentCurrentMealDetailBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.view.BaseActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CurrentMealDetailActivity : BaseActivity<FragmentCurrentMealDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    var calendar: Calendar = Calendar.getInstance()
    var date_filter: String? = null
    var str: String? = null
    var mealName = ArrayList(
        Arrays.asList("Breakfast", "Lunch", "Dinner"))
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        str = intent.getStringExtra("next")
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        date_filter = sdf.format(Date())
        startRelativeAnimation(binding.relWeightLayout)
        getWeekDays()
        if (str.equals("Next")){
            binding.llChosenMealNote.visibility = View.GONE
            binding.recyclerChosenMealList.visibility = View.VISIBLE
            setupChosenMealList()
        }else{
            binding.llChosenMealNote.visibility = View.VISIBLE
            binding.recyclerChosenMealList.visibility = View.GONE
        }
    }

    private fun setupChosenMealList() {
        binding.recyclerChosenMealList.apply {
            layoutManager = LinearLayoutManager(this@CurrentMealDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = ChosenMealListAdapter(this@CurrentMealDetailActivity, mealName, object :
                PositionItemClickListener<Int> {
                override fun onPositionItemSelected(item: String, postions: Int) {
                    startActivity(Intent(this@CurrentMealDetailActivity, ChosenMealDetailActivity::class.java))
                }

            })
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
                setWeekdayAdapter(dates, days, "")
            }
        }

        setWeekdayAdapter(dates, days, "")
    }

    private fun buttonClicks() {
        binding.imgBack.setOnClickListener {
           onBackPressed()
        }
        binding.mealCalender.setOnClickListener {
            startActivity(Intent(this@CurrentMealDetailActivity, MealPlanActivity::class.java))
        }

    }

    private fun setWeekdayAdapter(dates: Array<String?>, days: Array<String?>, backDate: String?) {
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
//                    val otFormat: java.text.DateFormat = SimpleDateFormat("MMMM dd, yyyy")
                    val date2: Date = itFormat.parse(outputDateStr)
                    val otDateStr: String = otFormat.format(date2)
//                    binding.etCalendar.setText(otDateStr)
                }
            })
    }

    override fun getViewBinding() = FragmentCurrentMealDetailBinding.inflate(layoutInflater)

    companion object {
        fun getBundle(s: String): Bundle? {
            val bundle = Bundle()
            return bundle
        }
    }

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