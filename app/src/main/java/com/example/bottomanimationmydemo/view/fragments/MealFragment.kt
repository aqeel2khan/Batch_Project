package com.example.bottomanimationmydemo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.MealBatchPlanAdapter
import com.example.bottomanimationmydemo.databinding.FilterDialogBinding
import com.example.bottomanimationmydemo.databinding.FragmentMealBinding
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.view.BaseFragment
import com.example.bottomanimationmydemo.view.activity.*
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MealFragment : BaseFragment<FragmentMealBinding>() {
    private val viewModel: AllViewModel by viewModels()
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
        setMealBatchPlanAdapter()
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
        dialogBinding.btnShowResult.setOnClickListener {
            //code for save week price
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun setMealBatchPlanAdapter() {
        binding.recyclerMealPlan.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            adapter = MealBatchPlanAdapter(context, name, object : PositionItemClickListener<Int> {
                override fun onPositionItemSelected(item: String, postions: Int) {
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