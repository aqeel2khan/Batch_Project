package com.dev.batchfinal.app_modules.meals.meal_unpurchase.view.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.MealBatchPlanAdapter
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.app_modules.activity.ChatActivity

import com.dev.batchfinal.app_modules.meals.meal_purchase.view.activity.CurrentMealDetailActivity
import com.dev.batchfinal.app_modules.meals.meal_purchase.view.activity.MealDetailsActivity
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyConstant.status
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.databinding.FilterDialogBinding
import com.dev.batchfinal.databinding.FragmentMealBinding
import com.dev.batchfinal.`interface`.MealListItemPosition
import com.dev.batchfinal.model.coach_filter_list.Experience
import com.dev.batchfinal.model.meal_filter_model.BatchGoal
import com.dev.batchfinal.model.meal_filter_model.MealCalory
import com.dev.batchfinal.model.meal_filter_model.MealTag
import com.dev.batchfinal.model.meal_list.MealList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel

import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zhy.view.flowlayout.CaloriesTagAdapter
import com.zhy.view.flowlayout.CaloriesTagFlowLayout
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.MealGoalTagAdapter
import com.zhy.view.flowlayout.MealGoalTagFlowLayout
import com.zhy.view.flowlayout.MealTagsTagAdapter
import com.zhy.view.flowlayout.MealTagsTagFlowLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    lateinit var dialogBinding: FilterDialogBinding
    private var mealList: ArrayList<MealList> = ArrayList()
    private var caloriesSize: Int = 0
    private var tagSize: Int = 0
    private var goalSize: Int = 0
    private var totalSelected: Int = 0

    private var calories_from: Int = 0
    private var calories_to: Int = 0
    private var calories_position: Int = -1
    private var teg_id: Int = 0
    private var teg_position: Int = -1
    private var goal_id: Int = -1
    private var goal_position: Int = -1

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
        searchByNameData()
        getMealList("")
        totalSelected = caloriesSize + tagSize + goalSize
    }

    private fun searchByNameData() {
        binding.etEmployeeSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                MyCustom.hideKeyboard(requireActivity())
                getMealList(v.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        binding.etEmployeeSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                getMealList(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    getMealList(s.toString())
                }
            }
        })
    }


    private fun getMealList(searchQuery: String) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            when (searchQuery) {
                "" -> {
                    showLoader()
                    authViewModel.mealListApiCall()
                }
                "meal_filter" -> {
                    showLoader()

                    MyConstant.jsonObject.addProperty("goal_id",goal_id)
                    MyConstant.jsonObject.addProperty("tag_id",teg_id)
                    MyConstant.jsonObject.addProperty("calories_from",calories_from)
                    MyConstant.jsonObject.addProperty("calories_to",calories_to)
                    authViewModel.mealListFilterApiCall(MyConstant.jsonObject)
                    binding.iconFilter.setBackgroundResource(R.drawable.ic_workout_filter_apply)

                }
                else -> {
                    hideLoader()
                    MyConstant.jsonObject.addProperty("keyword",binding.etEmployeeSearch.text!!.trim().toString())
                    // authViewModel.searchCoachListApiCall(jsonObject)
                    //authViewModel.searchCourseFilterListApiCall(jsonObject)
                    authViewModel.mealListFilterApiCall(MyConstant.jsonObject)

                }
            }
            authViewModel.mealListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.mealListResponse.removeObservers(this)
                        if (authViewModel.mealListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == status){
                                        mealList = response.data.data
                                        Log.d("list", response.data.data.toString())
                                        setMealBatchPlanAdapter(response.data.data)
                                    }
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.mealListResponse.removeObservers(this)
                        if (authViewModel.mealListResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    override fun getViewBinding() = FragmentMealBinding.inflate(layoutInflater)

    override fun onResume() {
        handleTitle(resources.getString(R.string.txt_meal_title))
        super.onResume()
    }
    private fun buttonClicks() {
        binding.cdCurrentMeal.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), CurrentMealDetailActivity::class.java))
        }
//        binding.btnCalculate.setOnClickListener {
//            requireContext().startActivity(Intent(requireContext(), QuestionActivity::class.java))
//        }
        binding.btnTalk.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), ChatActivity::class.java))
        }
        binding.iconFilter.setOnClickListener {
            showFilterDialog()
        }
    }

    private fun showFilterDialog() {
        dialogBinding = FilterDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        getMealFilterList(dialogBinding)
        dialogBinding.llMotivatorFilter.visibility = View.GONE
        dialogBinding.llMealFilter.visibility = View.VISIBLE
        dialogBinding.btnApply.setOnClickListener {
            //code for save week price
            getMealList("meal_filter")

            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getMealFilterList(dialogBinding: FilterDialogBinding) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.mealFilterApiCall()
            authViewModel.mealFilterResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.mealFilterResponse.removeObservers(this)
                        if (authViewModel.mealFilterResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("filterData", response.toString())
                                if (response.status == MyConstant.status){
                                    setMealCaloriesList(response.data.data.mealCalories, dialogBinding)
                                    setMealTagsList(response.data.data.mealTags, dialogBinding)
                                    setMealGoalList(response.data.data.batchGoals, dialogBinding)
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.mealFilterResponse.removeObservers(this)
                        if (authViewModel.mealFilterResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setMealCaloriesList(mealCalories: List<MealCalory>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idCalories.setAdapter(object : CaloriesTagAdapter<MealCalory>(mealCalories) {
            override fun getView(parent: FlowLayout?, position: Int, t: MealCalory?): View {
                val tv = mInflater.inflate(R.layout.tv, this@MealFragment.dialogBinding.idFlowlayout, false) as TextView
                tv.text = t!!.fromValue.toString() + "-" + t.toValue
                return tv
            }
            override fun setSelected(position: Int, t: MealCalory?): Boolean {
                return position ==calories_position
            }
        })

        dialogBinding.idCalories.setOnTagClickListener(CaloriesTagFlowLayout.OnTagClickListener { view, position, parent ->
//            typeId = workoutTypes[position].id
            calories_from=mealCalories[position].fromValue
            calories_to=mealCalories[position].toValue
            calories_position=position
            //requireActivity().showToast(mealCalories[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idCalories.setOnSelectListener(CaloriesTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
            var setList = this.dialogBinding.idCalories.selectedList
            caloriesSize = setList.size
            totalSelected = caloriesSize
            this.dialogBinding.btnApply.text = "Apply"+"("+ totalSelected + ")"
            Log.d("size", caloriesSize.toString())

        })
    }

    private fun setMealGoalList(batchGoals: List<BatchGoal>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        dialogBinding.idMealGoal.setAdapter(object : MealGoalTagAdapter<BatchGoal>(batchGoals) {
            override fun getView(parent: FlowLayout?, position: Int, t: BatchGoal?): View {
                val tv = mInflater.inflate(R.layout.tv, this@MealFragment.dialogBinding.idFlowlayout, false) as TextView
                tv.text = t!!.name
                return tv
            }
            override fun setSelected(position: Int, t: BatchGoal?): Boolean {
                return position ==goal_position
            }
        })

        dialogBinding.idMealGoal.setOnTagClickListener(MealGoalTagFlowLayout.OnTagClickListener { view, position, parent ->
//            typeId = workoutTypes[position].id
            goal_id=batchGoals[position].id
            goal_position=position

            //requireActivity().showToast(batchGoals[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idMealGoal.setOnSelectListener(MealGoalTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
            var setList = this.dialogBinding.idMealGoal.selectedList
            goalSize = setList.size
            totalSelected = caloriesSize + goalSize
            this.dialogBinding.btnApply.text = "Apply"+"("+ totalSelected + ")"
        })
    }

    private fun setMealTagsList(mealTags: List<MealTag>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        dialogBinding.idMealTags.setAdapter(object : MealTagsTagAdapter<MealTag>(mealTags) {
            override fun getView(parent: FlowLayout?, position: Int, t: MealTag?): View {
                val tv = mInflater.inflate(R.layout.tv, this@MealFragment.dialogBinding.idFlowlayout, false) as TextView
                tv.text = t!!.name
                return tv
            }
            override fun setSelected(position: Int, t: MealTag?): Boolean {
                return position ==teg_position
            }
        })

        dialogBinding.idMealTags.setOnTagClickListener(MealTagsTagFlowLayout.OnTagClickListener { view, position, parent ->
           // typeId = workoutTypes[position].id
            teg_id=mealTags[position].id
            teg_position=position
            //requireActivity().showToast(mealTags[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idMealTags.setOnSelectListener(MealTagsTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
            var setList = this.dialogBinding.idMealTags.selectedList
            tagSize = setList.size
            totalSelected = caloriesSize + goalSize + tagSize
            this.dialogBinding.btnApply.text = "Apply"+"("+ totalSelected + ")"
        })
    }

    private fun setMealBatchPlanAdapter(mealList: List<MealList>) {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = MealBatchPlanAdapter(context, mealList,"meal_meal", object : MealListItemPosition<Int> {
                override fun onMealListItemPosition(item: MealList, position: Int) {
                    activity!!.startActivity(Intent(requireContext(), MealDetailsActivity::class.java).putExtra("id", item.id.toString()))
                }
            })
        }

    }

    companion object {
        private const val id = "id"
        fun getBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(id, id)
            return bundle
        }
    }
}