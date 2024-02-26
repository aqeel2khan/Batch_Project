package com.example.bottomanimationmydemo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.MealBatchPlanAdapter
import com.example.bottomanimationmydemo.databinding.FilterDialogBinding
import com.example.bottomanimationmydemo.databinding.FragmentMealBinding
import com.example.bottomanimationmydemo.`interface`.MealListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.meal_list.MealList
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyConstant.status
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseFragment
import com.example.bottomanimationmydemo.view.activity.*
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import java.util.*

@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    lateinit var dialogBinding: FilterDialogBinding
    var name = ArrayList(
        Arrays.asList(
            "Batch Meal Plan", "Batch Meal Plan" )
    )

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()
//        getMealList()
//        setMealBatchPlanAdapter(response.data.mealList)
    }

  /*  private fun getMealList() {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.mealListApiCall()
            authViewModel.mealListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.mealListResponse.removeObservers(this)
                        if (authViewModel.mealListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == status){
//                                    courseList = response.data.list
                                    Log.d("list", response.data.mealList.toString())

//                                    allBatchesAdapter!!.setdata(response.data.list)

                                    Log.d("mealList", response.data.mealList.toString())
                                    setMealBatchPlanAdapter(response.data.mealList)

//                                    setAllBatchesAdapter(courseList)
                                }
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
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }*/

    override fun getViewBinding() = FragmentMealBinding.inflate(layoutInflater)

    override fun onResume() {
        handleTitle(resources.getString(R.string.txt_meal_title))
        super.onResume()
    }
    private fun buttonClicks() {
        binding.cdCurrentMeal.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), CurrentMealDetailActivity::class.java))
        }
        binding.btnCalculate.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), QuestionActivity::class.java))
        }
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

        dialogBinding.llMotivatorFilter.visibility = View.GONE
        dialogBinding.llMealFilter.visibility = View.VISIBLE
        dialogBinding.btnApply.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setMealBatchPlanAdapter(mealList: List<MealList>) {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = MealBatchPlanAdapter(context, mealList, object : MealListItemPosition<Int> {
                override fun onMealListItemPosition(item: MealList, position: Int) {
                    activity!!.startActivity(Intent(requireContext(), BatchMealPlanActivity::class.java))
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