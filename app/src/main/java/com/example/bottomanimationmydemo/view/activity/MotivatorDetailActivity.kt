package com.example.bottomanimationmydemo.view.activity

import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.AllBatchesAdapter
import com.example.bottomanimationmydemo.adapter.RecommendedProductListAdapter
import com.example.bottomanimationmydemo.custom.CustomToast.Companion.showToast
import com.example.bottomanimationmydemo.databinding.ActivityMotivatorDetailBinding
import com.example.bottomanimationmydemo.`interface`.CourseListItemPosition
import com.example.bottomanimationmydemo.`interface`.PositionItemClickListener
import com.example.bottomanimationmydemo.model.coach_detail_model.Data
import com.example.bottomanimationmydemo.model.course_model.ListData
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyConstant.jsonObject
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.MyUtils
import com.example.bottomanimationmydemo.view.BaseActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MotivatorDetailActivity : BaseActivity<ActivityMotivatorDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var courseList: ArrayList<ListData> = ArrayList()
    var coach_id: Int = 0

    var courseImg = ArrayList(Arrays.asList(R.drawable.sign_bg, R.drawable.food, R.drawable.profile_image, R.drawable.normal_boy))
    var techerName = ArrayList(
        Arrays.asList("Leggings in blue", "Training Top", "Yoga Main in Deep Blue", "Leggings in blue"))
    var profesion = ArrayList(Arrays.asList("\$35", "\$35", "\$35", "\$35"))

    var name = ArrayList(Arrays.asList("Weight Loss", "Workout Batch", "Workout Batch"))
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }

    override fun initUi() {
        coach_id = intent.getStringExtra("id")!!.toInt()
//        setAllBatchesAdapter()
        setUpRecommendedProductorAdapter()
        buttonClicks()
        val aniSlide: Animation = AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.rlMainLayout.startAnimation(aniSlide)

        getCoachDetailApi(coach_id)
        getCourseListApi("7")

    }

    private fun getCoachDetailApi(coach_id: Int) {
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            authViewModel.coachDetailApiCall(coach_id)
            authViewModel.coachDetailResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.coachDetailResponse.removeObservers(this)
                        if (authViewModel.coachDetailResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    Timber.tag("details").d(response.data.name)
                                    val coachData = response.data
                                    setUpDetails(coachData)
//                                    getCourseListApi(response.data.id.toString())
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.coachListResponse.removeObservers(this)
                        if (authViewModel.coachListResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }

                    else -> {}
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun getCourseListApi(coach_id: String) {
        if (CheckNetworkConnection.isConnection(this,binding.root, true)) {
            showLoader()
            jsonObject.addProperty("coach_id",coach_id)
            authViewModel.coachCourseListApiCall(jsonObject)
            authViewModel.coachCourseListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
//                        authViewModel.coachCourseListResponse.removeObservers(this)
//                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success){
                                    val courseList = response.data.list
                                    Log.d("list", courseList.toString())
                                    setAllBatchesAdapter(courseList)
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.coachCourseListResponse.removeObservers(this)
                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
                        hideLoader()
//                        snackBarWithRedBackground(binding.root,errorBody(binding.root.context, it.errorBody, ""))
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setUpDetails(coachData: Data) {
        MyUtils.loadBackgroundImage(binding.backgroundImg, MyConstant.IMAGE_BASE_URL + coachData.profilePhotoPath)
        binding.txtTrainerName.text = coachData.name
    }

    private fun buttonClicks() {
        binding.btnFollow.setOnClickListener {
            binding.btnFollow.visibility = View.GONE
            binding.btnFollowing.visibility = View.VISIBLE
        }
    }

    private fun setUpRecommendedProductorAdapter() {
        binding.recyclerRecommendedProduct.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerRecommendedProduct.adapter =
            RecommendedProductListAdapter(this, courseImg, techerName, profesion,
                object : PositionItemClickListener<Int> {
                    override fun onPositionItemSelected(item: String, postions: Int) {
//                    startActivity(Intent(this, MotivatorDetailActivity::class.java))
                    }
                })
    }

    private fun setAllBatchesAdapter(courseList: ArrayList<ListData>) {
        binding.recyclerBatches.apply {
            layoutManager = LinearLayoutManager(this@MotivatorDetailActivity, LinearLayoutManager.VERTICAL, false)
            adapter = AllBatchesAdapter(context, courseList, object :
                CourseListItemPosition<Int> {
                override fun onCourseListItemPosition(item: ListData, position: Int) {
                    val course_id = item.courseId
//                    activity!!.startActivity(Intent(requireContext(), CourseDetailActivity::class.java).putExtra("course_id", course_id.toString()))
                }
            })
            /* WorkoutBatchAdapter(this@MotivatorDetailActivity, courseList, object : PositionItemClickListener<Int> {
                    override fun onPositionItemSelected(item: String, postions: Int) {
                        startActivity(
                            Intent(
                                this@MotivatorDetailActivity,
                                WeightLossActivity::class.java
                            )
                        )
                    }

                })*/
        }
    }

    override fun getViewBinding() = ActivityMotivatorDetailBinding.inflate(layoutInflater)
}