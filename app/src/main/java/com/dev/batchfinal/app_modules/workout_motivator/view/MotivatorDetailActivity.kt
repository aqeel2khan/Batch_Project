package com.dev.batchfinal.app_modules.workout_motivator.view

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.MotivatorBatchesAdapter
import com.dev.batchfinal.adapter.RecommendedProductListAdapter
import com.dev.batchfinal.app_custom.CustomToast.Companion.showToast
import com.dev.batchfinal.databinding.ActivityMotivatorDetailBinding
import com.dev.batchfinal.`interface`.CourseListItemPosition
import com.dev.batchfinal.`interface`.PositionItemClickListener
import com.dev.batchfinal.model.coach_detail_model.Data
import com.dev.batchfinal.model.course_model.ListData
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyConstant.jsonObject
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.MyUtils
import com.dev.batchfinal.app_common.BaseActivity
import com.dev.batchfinal.app_modules.shopping.view.CourseDetailActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import timber.log.Timber
import java.util.*


@AndroidEntryPoint
class MotivatorDetailActivity : BaseActivity<ActivityMotivatorDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var courseList: ArrayList<ListData> = ArrayList()
    var coach_id: Int = 0

    var courseImg = ArrayList(Arrays.asList(R.drawable.sign_bg, R.drawable.food, R.drawable.profile_image,R.drawable.normal_boy))
    var techerName = ArrayList(Arrays.asList("Leggings in blue", "Training Top", "Yoga Main in Deep Blue", "Leggings in blue"))
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
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.rlMainLayout.startAnimation(aniSlide)

        getCoachDetailApi(coach_id)
        // getCourseListApi("7")
        if (coach_id != null) {
            getCourseListApi(coach_id.toString())
        }
//        getCourseDetailData(course_id)

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
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            jsonObject.addProperty("coach_id", coach_id)
            authViewModel.coachCourseListApiCall(jsonObject)
            authViewModel.coachCourseListResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
//                        authViewModel.coachCourseListResponse.removeObservers(this)
//                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    val courseList = response.data.list
                                    Log.d("list", courseList.toString())
                                    setAllBatchesAdapter(courseList)
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        hideLoader()
                    }
                    is Resource.Failure -> {
                        authViewModel.coachCourseListResponse.removeObservers(this)
                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
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

    private fun setUpDetails(coachData: Data) {
        MyUtils.loadBackgroundImage(
            binding.backgroundImg,
            MyConstant.IMAGE_BASE_URL + coachData.profilePhotoPath
        )
//        loadVimeoPromoVideo()
        binding.txtTrainerName.text = coachData.name
    }

//    private fun loadVimeoPromoVideo() {
//
////        ifecycle.addObserver(binding.vimeoPlayerView)
////        binding.backgroundImg
//        binding.vimeoPlayerView.initialize(true, 913068229)
//        //vimeoPlayerView.initialize(true, {YourPrivateVideoId}, "SettingsEmbeddedUrl")
//        //vimeoPlayerView.initialize(true, {YourPrivateVideoId},"VideoHashKey", "SettingsEmbeddedUrl")
//
//        binding.vimeoPlayerView.addTimeListener { second ->
////            binding.playerCurrentTimeTextView.text =
////                getString(R.string.player_current_time, second.toString())
//        }
//
//        binding.vimeoPlayerView.addErrorListener { message, method, name ->
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        }
//
//        binding.vimeoPlayerView.addReadyListener(object : VimeoPlayerReadyListener {
//            override fun onReady(
//                title: String?,
//                duration: Float,
//                textTrackArray: Array<TextTrack>
//            ) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onReady")
//            }
//
//            override fun onInitFailed() {
//             //   binding.playerStateTextView.text = getString(R.string.player_state, "onInitFailed")
//            }
//        })
//
//        binding.vimeoPlayerView.addStateListener(object : VimeoPlayerStateListener {
//            override fun onPlaying(duration: Float) {
//              //  binding.playerStateTextView.text = getString(R.string.player_state, "onPlaying")
//            }
//
//            override fun onPaused(seconds: Float) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onPaused")
//            }
//
//            override fun onEnded(duration: Float) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onEnded")
//            }
//        })
//
//
//    }

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
            layoutManager = LinearLayoutManager(
                this@MotivatorDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = MotivatorBatchesAdapter(
                context,
                courseList,
                object : CourseListItemPosition<Int> {
                    override fun onCourseListItemPosition(item: ListData, position: Int) {
                        val course_id = item.courseId
                        startActivity(
                            Intent(
                                this@MotivatorDetailActivity,
                                CourseDetailActivity::class.java
                            ).putExtra("course_id", course_id.toString())
                        )
                    }
                })
        }
    }
    /* WorkoutBatchAdapter(this@MotivatorDetailActivity, courseList, object : PositionItemClickListener<Int> {
          override fun onPositionItemSelected(item: String, postions: Int) {
              startActivity(
                  Intent(
                      this@MotivatorDetailActivity,
                      WeightLossActivity::class.java
                  )
              )
          }

      })*//*
        }
    }*/

    override fun getViewBinding() = ActivityMotivatorDetailBinding.inflate(layoutInflater)
}


/*
class MotivatorDetailActivity : BaseActivity<ActivityMotivatorDetailBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    var courseList: ArrayList<ListData> = ArrayList()
    var coach_id: Int = 0

    var courseImg = ArrayList(
        Arrays.asList(
            R.drawable.sign_bg,
            R.drawable.food,
            R.drawable.profile_image,
            R.drawable.normal_boy
        )
    )
    var techerName = ArrayList(
        Arrays.asList(
            "Leggings in blue",
            "Training Top",
            "Yoga Main in Deep Blue",
            "Leggings in blue"
        )
    )
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
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(applicationContext, R.anim.bottom_top)
        binding.rlMainLayout.startAnimation(aniSlide)

        getCoachDetailApi(coach_id)
        // getCourseListApi("7")
        if (coach_id != null) {
            getCourseListApi(coach_id.toString())
        }


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
        if (CheckNetworkConnection.isConnection(this, binding.root, true)) {
            showLoader()
            jsonObject.addProperty("coach_id", coach_id)
            authViewModel.coachCourseListApiCall(jsonObject)
            authViewModel.coachCourseListResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
//                        authViewModel.coachCourseListResponse.removeObservers(this)
//                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success) {
                                    val courseList = response.data.list
                                    Log.d("list", courseList.toString())
//                                    setAllBatchesAdapter(courseList)
                                }
                            }
                        }
                    }
                    is Resource.Loading -> {
                        hideLoader()
                    }
                    is Resource.Failure -> {
                        authViewModel.coachCourseListResponse.removeObservers(this)
                        if (authViewModel.coachCourseListResponse.hasObservers()) return@observe
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

    private fun setUpDetails(coachData: Data) {
        MyUtils.loadBackgroundImage(
            binding.backgroundImg,
            MyConstant.IMAGE_BASE_URL + coachData.profilePhotoPath
        )
//        loadVimeoPromoVideo()
        binding.txtTrainerName.text = coachData.name
    }

//    private fun loadVimeoPromoVideo() {
//
////        ifecycle.addObserver(binding.vimeoPlayerView)
////        binding.backgroundImg
//        binding.vimeoPlayerView.initialize(true, 913068229)
//        //vimeoPlayerView.initialize(true, {YourPrivateVideoId}, "SettingsEmbeddedUrl")
//        //vimeoPlayerView.initialize(true, {YourPrivateVideoId},"VideoHashKey", "SettingsEmbeddedUrl")
//
//        binding.vimeoPlayerView.addTimeListener { second ->
////            binding.playerCurrentTimeTextView.text =
////                getString(R.string.player_current_time, second.toString())
//        }
//
//        binding.vimeoPlayerView.addErrorListener { message, method, name ->
//            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//        }
//
//        binding.vimeoPlayerView.addReadyListener(object : VimeoPlayerReadyListener {
//            override fun onReady(
//                title: String?,
//                duration: Float,
//                textTrackArray: Array<TextTrack>
//            ) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onReady")
//            }
//
//            override fun onInitFailed() {
//             //   binding.playerStateTextView.text = getString(R.string.player_state, "onInitFailed")
//            }
//        })
//
//        binding.vimeoPlayerView.addStateListener(object : VimeoPlayerStateListener {
//            override fun onPlaying(duration: Float) {
//              //  binding.playerStateTextView.text = getString(R.string.player_state, "onPlaying")
//            }
//
//            override fun onPaused(seconds: Float) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onPaused")
//            }
//
//            override fun onEnded(duration: Float) {
//               // binding.playerStateTextView.text = getString(R.string.player_state, "onEnded")
//            }
//        })
//
//
//    }

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
            layoutManager = LinearLayoutManager(
                this@MotivatorDetailActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = MotivatorBatchesAdapter(
                context,
                courseList,
                object : CourseListItemPosition<Int> {
                    override fun onCourseListItemPosition(item: ListData, position: Int) {
                        val course_id = item.courseId
                        startActivity(
                            Intent(
                                this@MotivatorDetailActivity,
                                CourseDetailActivity::class.java
                            ).putExtra("course_id", course_id.toString())
                        )
                    }
                })
        }
    }
    */
/* WorkoutBatchAdapter(this@MotivatorDetailActivity, courseList, object : PositionItemClickListener<Int> {
                    override fun onPositionItemSelected(item: String, postions: Int) {
                        startActivity(
                            Intent(
                                this@MotivatorDetailActivity,
                                WeightLossActivity::class.java
                            )
                        )
                    }

                })*//*
*/
/*
        }
    }*//*


    override fun getViewBinding() = ActivityMotivatorDetailBinding.inflate(layoutInflater)
}*/
