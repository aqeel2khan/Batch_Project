package com.example.bottomanimationmydemo.view.fragments

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.CourseOrderAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.FragmentScaningBinding
import com.example.bottomanimationmydemo.databinding.HomeMealDialogBinding
import com.example.bottomanimationmydemo.`interface`.CourseOrderListItemPosition
import com.example.bottomanimationmydemo.model.courseorderlist.Data
import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.view.BaseFragment
import com.example.bottomanimationmydemo.view.activity.WeightLossActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource

@AndroidEntryPoint
class ScanningFragment : BaseFragment<FragmentScaningBinding>() {
    private lateinit var courseData: Data
    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    lateinit var dialogBinding: HomeMealDialogBinding

    var courseList: ArrayList<OrderList> = ArrayList()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


    override fun initUi() {
        buttonClicks()
        getCourseOrderListApi()
        setAllCourseOrderAdapter(courseList) //hide code
    }

    private fun buttonClicks() {
        binding.cdCurrentMeal.setOnClickListener {
            findNavController().navigate(
                R.id.action_scanFragment_to_mealBatchFragment,
                MealBatchFragment.getBundle("")
            )
        }
//        binding.currentWorkoutCard.setOnClickListener {
//            requireContext().startActivity(Intent(requireContext(), WeightLossActivity::class.java))
//        }

        binding.cardHeartRate.setOnClickListener {
            showBottomDialog("heart")
        }
        binding.cardSteps.setOnClickListener {
            showBottomDialog("steps")
        }
        binding.llSleep.setOnClickListener {
            showBottomDialog("sleep")
        }
        binding.llCaloriece.setOnClickListener {
            showBottomDialog("calories")
        }
        binding.llWater.setOnClickListener {
            showBottomDialog("water")
        }
    }

    private fun showBottomDialog(dialogType: String) {
        dialogBinding = HomeMealDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)

        dialogBinding.llChange.visibility = View.GONE
        dialogBinding.llFreeze.visibility = View.GONE
        if (dialogType == "heart") {
            dialogBinding.llHeartRate.visibility = View.VISIBLE
            dialogBinding.btnOkay.setOnClickListener {
                dialog.dismiss()
            }
        } else if (dialogType == "steps") {
            dialogBinding.txtHeader.text = resources.getString(R.string.txt_steps)
            dialogBinding.llSteps.visibility = View.VISIBLE
            dialogBinding.btnOkay.setOnClickListener {
                dialog.dismiss()
            }
        } else if (dialogType == "sleep") {
            dialogBinding.txtHeader.text = resources.getString(R.string.txt_sleep)
            dialogBinding.llSleep.visibility = View.VISIBLE
            dialogBinding.btnOkay.setOnClickListener {
                dialog.dismiss()
            }
        } else if (dialogType == "calories") {
            dialogBinding.txtHeader.text = resources.getString(R.string.txt_calories)
            dialogBinding.llCalories.visibility = View.VISIBLE
            dialogBinding.btnOkay.setOnClickListener {
                dialog.dismiss()
            }
        } else if (dialogType == "water") {
            dialogBinding.txtHeader.text = resources.getString(R.string.txt_water)
            dialogBinding.llCalories.visibility = View.VISIBLE
            dialogBinding.btnOkay.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun getCourseOrderListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(), binding.root, true)) {
            showLoader()
            authViewModel.courseOrderListApiCall("Bearer " + sharedPreferences.token)
            Log.d("Token","Bearer " + sharedPreferences.token);
            authViewModel.courseOrderListResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.courseOrderListResponse.removeObservers(this)
                        if (authViewModel.courseOrderListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("response_order",response.data.toString())
                                if (response.status == MyConstant.success) {
                                    courseList = response.data.list
                                    courseData = response.data
                                    setAllCourseOrderAdapter(courseList)
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        hideLoader()
                    }
                    is Resource.Failure -> {
                        authViewModel.courseOrderListResponse.removeObservers(this)
                        if (authViewModel.courseOrderListResponse.hasObservers()) return@observe
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

    private fun setAllCourseOrderAdapter(courseList: ArrayList<OrderList>) {
        binding.recyclerCourseOrder.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCourseOrder.adapter = CourseOrderAdapter(context, courseList, object :
            CourseOrderListItemPosition<Int> {
            override fun onCourseOrderListItemPosition(item: OrderList, position: Int) {
//                val course_id = item.courseId
//                courseDetailData as Serializable
//                activity!!.startActivity(Intent(requireContext(), CourseDetailActivity::class.java).putExtra("course_id", course_id.toString()))
                val gson = Gson()
                requireContext().startActivity(
                    Intent(
                        requireContext(),
                        WeightLossActivity::class.java
                    ).putExtra("order_list", gson.toJson(item))
                )
            }
        })
    }

    override fun getViewBinding() = FragmentScaningBinding.inflate(layoutInflater)
    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_setting, container, false)
        binding = FragmentScaningBinding.inflate(layoutInflater)
        return binding.root
    }*/
}