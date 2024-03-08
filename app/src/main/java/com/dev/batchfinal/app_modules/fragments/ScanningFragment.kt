package com.dev.batchfinal.app_modules.fragments

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.CourseOrderAdapter
import com.dev.batchfinal.app_custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.FragmentScaningBinding
import com.dev.batchfinal.databinding.HomeMealDialogBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.dev.batchfinal.custom.CustomToast.Companion.showToast
//import com.dev.batchfinal.databinding.FragmentScaningBinding
//import com.dev.batchfinal.databinding.HomeMealDialogBinding
//import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.dev.batchfinal.`interface`.CourseOrderListItemPosition
import com.dev.batchfinal.`interface`.MealSubscribeListPosition
import com.dev.batchfinal.model.courseorderlist.Data
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListRequest
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListResponse
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.app_modules.account.view.LoginActivity
import com.dev.batchfinal.app_modules.activity.WeightLossActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
//import com.example.bottomanimationmydemo.R
//import com.example.bottomanimationmydemo.adapter.CourseOrderAdapter
//import com.dev.batchfinal.adapter.MealSubscribeListAdapter
//import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
//import com.example.bottomanimationmydemo.databinding.FragmentScaningBinding
//import com.example.bottomanimationmydemo.databinding.HomeMealDialogBinding
//import com.example.bottomanimationmydemo.`interface`.CourseOrderListItemPosition
//import com.dev.batchfinal.`interface`.MealSubscribeListPosition
//import com.example.bottomanimationmydemo.model.chosen_meal_details_model.ChosenMealDetailsResponse.InternalData
//import com.example.bottomanimationmydemo.model.courseorderlist.Data
//import com.example.bottomanimationmydemo.model.courseorderlist.OrderList
//import com.example.bottomanimationmydemo.model.meal_plan_subscribe.MealSubscribedRequest
//import com.example.bottomanimationmydemo.model.subscribe_list_model.MealSubscribeListRequest
//import com.example.bottomanimationmydemo.model.subscribe_list_model.MealSubscribeListResponse.InternalDatum
//import com.example.bottomanimationmydemo.out.AuthViewModel
//import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
//import com.example.bottomanimationmydemo.utils.MyConstant
//import com.example.bottomanimationmydemo.utils.MyCustom
//import com.example.bottomanimationmydemo.view.BaseFragment
//import com.example.bottomanimationmydemo.view.activity.CurrentMealDetailActivity
//import com.example.bottomanimationmydemo.view.activity.WeightLossActivity
//import com.example.bottomanimationmydemo.viewmodel.AllViewModel
//import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.view.activity.CurrentMealDetailActivity
import com.dev.batchfinal.adapter.MealSubscribeListAdapter
import com.dev.batchfinal.databinding.FragmentScaningBinding
import com.dev.batchfinal.databinding.HomeMealDialogBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding

//import com.example.bottomanimationmydemo.databinding.FragmentScaningBinding
//import com.example.bottomanimationmydemo.databinding.HomeMealDialogBinding
//import com.example.bottomanimationmydemo.databinding.ProfileEditDialogBinding

@AndroidEntryPoint
class ScanningFragment : BaseFragment<FragmentScaningBinding>() {
    private lateinit var courseData: Data
    lateinit var dialogOptionBinding: ProfileEditDialogBinding
    private lateinit var sessionManager: UserSessionManager


    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    lateinit var dialogBinding: HomeMealDialogBinding

    var courseList: ArrayList<OrderList> = ArrayList()
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


    override fun initUi() {
        sessionManager= UserSessionManager(requireContext())
        buttonClicks()
        getMealSubscribeListApi()
        getCourseOrderListApi()
        setAllCourseOrderAdapter(courseList) //hide code
    }
     //TODO
    private fun buttonClicks() {
        binding.cdCurrentMeal.setOnClickListener {
            findNavController().navigate(
                R.id.action_scanFragment_to_mealBatchFragment,
                MealBatchFragment.getBundle("","")
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

    private fun getMealSubscribeListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(), binding.root, true)) {
            showLoader()
            val mealSubscribeListRequest = MealSubscribeListRequest()
            mealSubscribeListRequest.userId=sharedPreferences.userId
            authViewModel.mealSubscribeListApiCall(mealSubscribeListRequest)
            Log.d("Token","Bearer " + sharedPreferences.token);
            authViewModel.mealSubscribeListResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mealSubscribeListResponse.removeObservers(this)
                        if (authViewModel.mealSubscribeListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("response_order",response.data.toString())
                                if (response.status == MyConstant.success) {

                                    setAllMealSubscribeListAdapter(response.data.internalData)
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        hideLoader()
                    }
                    is Resource.Failure -> {
                        authViewModel.mealSubscribeListResponse.removeObservers(this)
                        if (authViewModel.mealSubscribeListResponse.hasObservers()) return@observe
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

    private fun getCourseOrderListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(), binding.root, true)) {
            showLoader()
            //authViewModel.courseOrderListApiCall("Bearer " + sharedPreferences.token)
            authViewModel.courseOrderListApiCall("Bearer " + sessionManager.getUserToken())
            Log.e("Token","Bearer " + sessionManager.getUserToken());
            authViewModel.courseOrderListResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.courseOrderListResponse.removeObservers(this)
                        if (authViewModel.courseOrderListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.e("RES_SCANNING",response.data.toString())
                                // Log.d("response_order",response.data.toString())
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
                        Log.e("RES_SCANNING",it.errorBody.toString())
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                       // askUserForLogin(MyCustom.parseErrorBody(binding.root.context, it.errorBody, "").toString())

                        //askUserForLogin("Required authorization to access scanning batch.")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setAllMealSubscribeListAdapter(internalDatum: List<MealSubscribeListResponse.InternalDatum>) {
        binding.recyclerMealSubscribe.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMealSubscribe.adapter = MealSubscribeListAdapter(context, internalDatum, object :
            MealSubscribeListPosition<Int> {
            override fun onMealSubscribeListItemPosition(item: MealSubscribeListResponse.InternalDatum, position: Int) {
                requireContext().startActivity(Intent(requireContext(), CurrentMealDetailActivity::class.java)
                    .putExtra("meal_id",item.id.toString())
                    .putExtra("subscribe_id",item.subscribedId.toString())
                )

              /*  findNavController().navigate(
                    R.id.action_scanFragment_to_mealBatchFragment,
                    MealBatchFragment.getBundle(item.id.toString(),item.subscribedId.toString())

                ) */           }
        })
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
                requireContext().startActivity(Intent(requireContext(), WeightLossActivity::class.java)
                    .putExtra("order_list", gson.toJson(item))


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

    private fun askUserForLogin(sessionInfo:String) {
        dialogOptionBinding = ProfileEditDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogOptionBinding.root)
        dialogOptionBinding.llNotifiPlan.visibility = View.GONE
        dialogOptionBinding.llLogOut.visibility = View.GONE
        dialogOptionBinding.llLogin.visibility=View.VISIBLE
        dialogOptionBinding.txtLoginContent.text =sessionInfo
        dialogOptionBinding.btnOkay.setOnClickListener {
            //code for save week price
            requireContext().startActivity(Intent(requireContext(), LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
            dialog.dismiss()
        }

        dialog.show()
    }


}