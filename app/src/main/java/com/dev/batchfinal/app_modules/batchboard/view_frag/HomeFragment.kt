package com.dev.batchfinal.app_modules.batchboard.view_frag

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.*
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.databinding.FragmentHomeBinding
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.app_modules.workout_motivator.view.MotivatorDetailActivity
import com.dev.batchfinal.app_modules.activity.WeightLossActivity
import com.dev.batchfinal.app_modules.batchboard.model.toprated.TopRatedResponse.Internaldatum
import com.dev.batchfinal.app_modules.fragments.NetworkConnectivityFragment.Companion.showLoader
import com.dev.batchfinal.app_modules.meals.meal_purchase.view.activity.MealDetailsActivity
import com.dev.batchfinal.app_modules.shopping.view.CourseDetailActivity
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.databinding.FragmentTrainingBinding
import com.dev.batchfinal.`interface`.CoachListItemPosition
import com.dev.batchfinal.`interface`.CourseListItemPosition
import com.dev.batchfinal.`interface`.MealListItemPosition
import com.dev.batchfinal.model.coach_list_model.Data
import com.dev.batchfinal.model.course_model.ListData
import com.dev.batchfinal.model.meal_list.MealList
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.jetbrains.annotations.ApiStatus
import timber.log.Timber
import java.util.ArrayList
import java.util.Arrays


@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    private val images: IntArray = intArrayOf(
        R.drawable.exercise_image,
        R.drawable.food,
        R.drawable.meal_bg,
        R.drawable.exercise_image
    )
    var name = ArrayList(
        Arrays.asList("Workout Batch", "Weight Loss", "Workout Batch" ))
    var techerName = ArrayList(
        Arrays.asList(
            "Bessie Cooper", "Leslie Alexander", "Jenny Wilson", "Bessie Cooper", "Leslie Alexander", "Jenny Wilson" )
    )
    var profesion = ArrayList(
        Arrays.asList(
            "Yoga, pilates", "Cardio stretching", "Mobility", "Yoga, pilates", "Cardio stretching", "Mobility" )
    )
    var courseImg = ArrayList(Arrays.asList(
        R.drawable.avtar, R.drawable.normal_boy,
        R.drawable.profile_image, R.drawable.avtar, R.drawable.normal_boy,
        R.drawable.profile_image)
    )
    private var adapter: SliderAdapter? = null


    override fun getViewModel(): BaseViewModel {
        return  viewModel
    }

    override fun initUi() {
        buttonClicks()
        adapter = SliderAdapter(images)
        binding.imageSlider.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR;
        binding.imageSlider.setSliderAdapter(adapter!!)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.SLIDE)
        // on below line setting scroll time for slider view
        binding.imageSlider.scrollTimeInSec = 3
        // on below line setting auto cycle for slider view.
        binding.imageSlider.isAutoCycle = true
        binding.imageSlider.startAutoCycle()
        //setUpBatchesAdapter()
        getCourseListApi()
    }

    override fun getViewBinding()= FragmentHomeBinding.inflate(layoutInflater)


    // Training Api Integration
    private fun getCourseListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            authViewModel.courseListApiCall()
            authViewModel.courseListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.courseListResponse.removeObservers(this)
                        if (authViewModel.courseListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.success){
                                        setAllBatchesAdapter(response.data.list)
                                        getCoachListApi()

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
                        authViewModel.courseListResponse.removeObservers(this)
                        if (authViewModel.courseListResponse.hasObservers()) return@observe
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
    private fun setAllBatchesAdapter(courseList: ArrayList<ListData>) {
        binding.recyclerWorkbatches.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
       var allBatchesAdapter = AllBatchesAdapter(context, courseList,"home_screen", object :
            CourseListItemPosition<Int> {
            override fun onCourseListItemPosition(item: ListData, position: Int) {
                val course_id = item.courseId
                activity!!.startActivity(Intent(requireContext(), CourseDetailActivity::class.java).putExtra("course_id", course_id.toString()))
            }
        })
        binding.recyclerWorkbatches.adapter = allBatchesAdapter
    }

    // Motivator api Integration
    private fun getCoachListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            authViewModel.coachListApiCall()

            authViewModel.coachListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.coachListResponse.removeObservers(this)
                        if (authViewModel.coachListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success){
                                    val coachList = response.data
                                    setMotivatorListAdapter(coachList)
                                    getMealList()
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.coachListResponse.removeObservers(this)
                        if (authViewModel.coachListResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setMotivatorListAdapter(coachList: List<Data>) {
        binding.recyclerMotivators.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MotivatorListAdapter(requireContext(), coachList,"motivator_home",
                object : CoachListItemPosition<Int> {
                    override fun onCoachListItemPosition(item: Data, position: Int) {
                        val coach_id = item.id
                        Log.e("id", coach_id.toString())
                        Timber.tag("id").i(coach_id.toString())
                        activity!!.startActivity(Intent(context, MotivatorDetailActivity::class.java).putExtra("id", item.id.toString()))
                    }
                })
        }
    }

    // Meal api Integration
    private fun getMealList() {
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
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.status){
                                        Log.d("list", response.data.data.toString())
                                        setMealBatchPlanAdapter(response.data.data)
                                        getTopRatedList()
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

    private fun setMealBatchPlanAdapter(mealList: List<MealList>) {
        binding.recyclerMeal.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MealBatchPlanAdapter(context, mealList,"meal_home", object : MealListItemPosition<Int> {
                override fun onMealListItemPosition(item: MealList, position: Int) {
                    activity!!.startActivity(Intent(requireContext(), MealDetailsActivity::class.java).putExtra("id", item.id.toString()))
                }
            })
        }

    }


    private fun getTopRatedList() {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.topRatedApiCall()
            authViewModel.topRatedResponseResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.topRatedResponseResponse.removeObservers(this)
                        if (authViewModel.topRatedResponseResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            try {
                                it.let {
                                    val response = it.value
                                    if (response.status == MyConstant.status){
                                        Log.d("list", response.data.data.toString())
                                        setTopRatedMealAdapter(response.data.data)
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
                        authViewModel.topRatedResponseResponse.removeObservers(this)
                        if (authViewModel.topRatedResponseResponse.hasObservers()) return@observe
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

    private fun setTopRatedMealAdapter(mealList: List<MealList>) {
        binding.recyclerToprated.apply {
            layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
            adapter = MealBatchPlanAdapter(context, mealList,"meal_top_home", object : MealListItemPosition<Int> {
                override fun onMealListItemPosition(item: MealList, position: Int) {
                    activity!!.startActivity(Intent(requireContext(), MealDetailsActivity::class.java).putExtra("id", item.id.toString()))
                }
            })
        }

    }


    private fun buttonClicks() {
        /*binding.btnTraining.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_trainingFragment,
                TrainingFragment.getBundle("")
            )
        }

        binding.btnMeal.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_mealFragment,
                MealFragment.getBundle("")
            )
        }
        binding.btnShop.setOnClickListener {
            findNavController().navigate(
                R.id.action_batchboardFragment_to_shopFragment,
                ShoppingFragment.getBundle("")
            )
        }*/
    }
}