package com.example.bottomanimationmydemo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.example.bottomanimationmydemo.MainActivity
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.databinding.FragmentMealBatchBinding
import com.example.bottomanimationmydemo.databinding.HomeMealDialogBinding
import com.example.bottomanimationmydemo.model.StatusModel
import com.example.bottomanimationmydemo.view.BaseFragment
import com.example.bottomanimationmydemo.view.activity.CurrentMealDetailActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MealBatchFragment : BaseFragment<FragmentMealBatchBinding>() {
    private val viewModel: AllViewModel by viewModels()
    lateinit var dialogBinding: HomeMealDialogBinding
    var statusList = ArrayList<StatusModel>()
    var statusValue: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().startActivity(
                    Intent(requireContext(), MainActivity::class.java))
                // in here you can do logic when backPress is clicked
            }
        })
    }

    companion object {
        private const val id = "id"
        fun getBundle(id: String): Bundle {
            val bundle = Bundle()
            bundle.putString(id, id)
            return bundle
        }
    }

    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        buttonClicks()


/*
        if (statusValue == "Freeze"){
            binding.statusFreeze.visibility = View.VISIBLE
            binding.statusActive.visibility = View.GONE
            binding.statusFreeze.text = statusValue
            binding.btnFreeze.text = resources.getString(R.string.txt_un_freeze)
        }else{
            binding.statusFreeze.visibility = View.GONE
            binding.statusActive.visibility = View.VISIBLE
            binding.statusActive.text = resources.getString(R.string.txt_status_active)
            binding.btnFreeze.text = resources.getString(R.string.txt_freeze)
        }*/

        statusList = arrayListOf<StatusModel>(
            StatusModel(id = -1, status = "Select Status"),
            StatusModel(id = 0, status = "Pending"),
            StatusModel(id = 1, status = "Approved"),
            StatusModel(id = 3, status = "Denied/Rejected")
        )
    }

    private fun buttonClicks() {
        binding.btnChange.setOnClickListener {
            showBottomDialog("change")
        }
        binding.btnFreeze.setOnClickListener {
            showBottomDialog("freeze")
        }
        binding.btnUnfreeze.setOnClickListener {
            showBottomDialog("unFreeze")
        }
        binding.imgNext.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), CurrentMealDetailActivity::class.java))
        }

    }

    private fun showBottomDialog(dialogType: String) {
        dialogBinding = HomeMealDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        if (dialogType.equals("freeze")){
            dialogBinding.llFreeze.visibility = View.VISIBLE
            dialogBinding.llFreezePlan.setOnClickListener {
                selectLeaveStatusAdapter(statusList, dialogBinding)
            }
            dialogBinding.btnConfirm.setOnClickListener {
                //code for save week price
                binding.statusFreeze.visibility = View.VISIBLE
                binding.statusActive.visibility = View.GONE
                statusValue = resources.getString(R.string.txt_freeze_red)
                binding.statusFreeze.text = statusValue

                binding.btnUnfreeze.visibility = View.VISIBLE
                binding.btnFreeze.visibility = View.GONE
                dialog.dismiss()
            }
        }else if(dialogType.equals("unFreeze")){
            dialogBinding.llFreeze.visibility = View.VISIBLE
            dialogBinding.txtTitle.text = resources.getString(R.string.txt_un_freeze)
            dialogBinding.txtLogContent.text = resources.getString(R.string.txt_un_freeze_plan_content)
            dialogBinding.llFreezePlan.visibility = View.GONE
            dialogBinding.btnConfirm.setOnClickListener {
                //code for save week price
                binding.statusFreeze.visibility = View.GONE
                binding.statusActive.visibility = View.VISIBLE

                binding.btnUnfreeze.visibility = View.GONE
                binding.btnFreeze.visibility = View.VISIBLE
                dialog.dismiss()
            }
        }else if (dialogType.equals("change")) {
            dialogBinding.llChange.visibility = View.VISIBLE
            dialogBinding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun selectLeaveStatusAdapter(
        statusList: ArrayList<StatusModel>,
        dialogBinding: HomeMealDialogBinding
    ) {
        dialogBinding.spinnerFreeze.visibility = View.VISIBLE
        dialogBinding.spinnerFreeze.performClick()

        val Information = ArrayList<String>()
        val informationData = statusList
        val indId = ArrayList<Int>()
//        Information.add("Select Status")
//        indId.add(0)
        for (items in informationData) {
            Information.add(items.status.toString())
            indId.add(items.id!!.toInt())
        }
        val activityAdapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, Information)
        activityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinnerFreeze.adapter = activityAdapter
        dialogBinding.spinnerFreeze.post(
            Runnable {
                dialogBinding.spinnerFreeze.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                            val txtData = dialogBinding.spinnerFreeze.selectedView as TextView
                            if (p2 != 0) {
                                var industryName = Information[p2]
                                dialogBinding.txtFreeze.text = industryName
                                dialogBinding.spinnerFreeze.visibility = View.GONE
                                txtData.visibility = View.VISIBLE

                            } else {
                                dialogBinding.spinnerFreeze.visibility = View.VISIBLE

                            }
                        }

                        override fun onNothingSelected(p0: AdapterView<*>?) {
                            val txtData = dialogBinding.spinnerFreeze.selectedView as TextView
                            txtData.visibility = View.VISIBLE
                        }
                    }
            }
        )
    }

    override fun getViewBinding() = FragmentMealBatchBinding.inflate(layoutInflater)

    override fun onResume() {
        super.onResume()
        handleTitle("Meal Batch")
        handleBottomBar(false)
    }

}