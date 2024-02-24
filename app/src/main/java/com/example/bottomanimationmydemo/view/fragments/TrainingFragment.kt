package com.example.bottomanimationmydemo.view.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bottomanimationmydemo.R
import com.example.bottomanimationmydemo.adapter.AllBatchesAdapter
import com.example.bottomanimationmydemo.adapter.MotivatorListAdapter
import com.example.bottomanimationmydemo.databinding.FilterDialogBinding
import com.example.bottomanimationmydemo.databinding.FragmentTrainingBinding
import com.example.bottomanimationmydemo.`interface`.CoachListItemPosition
import com.example.bottomanimationmydemo.`interface`.CourseListItemPosition
import com.example.bottomanimationmydemo.model.coach_list_model.Data
import com.example.bottomanimationmydemo.model.course_filter_model.BatchGoal
import com.example.bottomanimationmydemo.model.course_filter_model.BatchLevel
import com.example.bottomanimationmydemo.model.course_filter_model.WorkoutType
import com.example.bottomanimationmydemo.model.course_model.ListData
import com.example.bottomanimationmydemo.out.AuthViewModel
import com.example.bottomanimationmydemo.utils.CheckNetworkConnection
import com.example.bottomanimationmydemo.utils.MyConstant
import com.example.bottomanimationmydemo.utils.MyCustom
import com.example.bottomanimationmydemo.utils.showToast
import com.example.bottomanimationmydemo.view.BaseFragment
import com.example.bottomanimationmydemo.view.activity.MotivatorDetailActivity
import com.example.bottomanimationmydemo.view.activity.CourseDetailActivity
import com.example.bottomanimationmydemo.viewmodel.AllViewModel
import com.example.bottomanimationmydemo.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.zhy.view.flowlayout.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.network.Resource
import timber.log.Timber
import java.util.Arrays

@AndroidEntryPoint
class TrainingFragment : BaseFragment<FragmentTrainingBinding>() {
    private val authViewModel by viewModels<AuthViewModel>()
    lateinit var dialogBinding: FilterDialogBinding
    var courseList: ArrayList<ListData> = ArrayList()
    var name = ArrayList(
        Arrays.asList(
            "Workout Batch", "Weight Loss", "Workout Batch" )
    )
    var typeId: Int = 0
    var goalId: Int = 0
    var levelId: Int = 0

    private val viewModel: AllViewModel by viewModels()

    override fun getViewModel(): BaseViewModel {
        return  viewModel
    }

    override fun initUi() {
        buttonClicks()
        getCourseListApi()
        setAllBatchesAdapter(courseList) //hide code
    }

    override fun getViewBinding() = FragmentTrainingBinding.inflate(layoutInflater)

    private fun buttonClicks() {
        binding.llBatch.setOnClickListener {
            binding.llBatch.background = resources.getDrawable(R.drawable.tab_sele_bg)
            binding.llMotivator.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            binding.tvBatch.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvMotivator.setTextColor(ContextCompat.getColor(requireContext(),R.color.welcome_txt_gry))
//            binding.llMotivator.setBackgroundColor(Color.parseColor("#EEE8E8"))
            binding.rlMotivatorSearch.visibility = View.GONE
//            setAllBatchesAdapter()
            getCourseListApi()
            binding.rlWorkoutFilter.visibility = View.VISIBLE
        }
        binding.llMotivator.setOnClickListener {
            binding.llBatch.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            binding.llMotivator.background = resources.getDrawable(R.drawable.tab_sele_bg)
            binding.tvBatch.setTextColor(ContextCompat.getColor(requireContext(),R.color.welcome_txt_gry))
            binding.tvMotivator.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.rlMotivatorSearch.visibility = View.VISIBLE
            binding.rlWorkoutFilter.visibility = View.GONE
            searchCoachData()
            getCoachListApi("")
        }
        binding.rlWorkoutFilter.setOnClickListener {
            showFilterDialog("workoutFilter")
        }
        binding.iconFilter.setOnClickListener {
            showFilterDialog("motivatoFilter")
        }
    }

    /*course list api code*/
    private fun getCourseListApi() {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.courseListApiCall()
            authViewModel.courseListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.courseListResponse.removeObservers(this)
                        if (authViewModel.courseListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                if (response.status == MyConstant.success){
                                    courseList = response.data.list
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
                        authViewModel.courseListResponse.removeObservers(this)
                        if (authViewModel.courseListResponse.hasObservers()) return@observe
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

    private fun searchCoachData() {
        binding.editQuery.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                MyCustom.hideKeyboard(requireActivity())
                getCoachListApi(v.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        binding.editQuery.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
//                getCoachListApi(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    getCoachListApi(s.toString())
                }
            }
        })
    }
    /*coach list api code*/
    private fun getCoachListApi(searchQuery: String) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            if (searchQuery == ""){
                showLoader()
                authViewModel.coachListApiCall()
            }else{
                hideLoader()
                MyConstant.jsonObject.addProperty("keyword",binding.editQuery.text.trim().toString())
                authViewModel.searchCoachListApiCall(MyConstant.jsonObject)
            }

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
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    /* comment code */
    private fun showFilterDialog(filterType: String) {
        dialogBinding = FilterDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        if (filterType.equals("workoutFilter")){
            getCourseFilter(dialogBinding)
            dialogBinding.llWorkoutFilter.visibility = View.VISIBLE
            val count = typeId + goalId + levelId
            dialogBinding.btnApply.text = "Apply"+"("+ count + ")"
            dialogBinding.btnApply.setOnClickListener {
                //code for save week price
//                dialogBinding.btnApply.text = "Apply()"
                //course filter by list
                MyConstant.jsonObject.addProperty("course_level","2")
                MyConstant.jsonObject.addProperty("workout_type_id","2")
                MyConstant.jsonObject.addProperty("goal_id","2")
                searchCourseListByFilterApi(MyConstant.jsonObject, dialogBinding)
                dialog.dismiss()
            }
        }else{
            getCoachFilter(dialogBinding)
            dialogBinding.llMotivatorFilter.visibility = View.VISIBLE
            dialogBinding.btnApply.setOnClickListener {
                //code for save week price
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun searchCourseListByFilterApi(jsonObject: JsonObject, dialogBinding: FilterDialogBinding) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.searchCourseFilterListApiCall(jsonObject)
            authViewModel.searchCourseFilterListResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.searchCourseFilterListResponse.removeObservers(this)
                        if (authViewModel.searchCourseFilterListResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("filterData", response.toString())
                                if (response.status == MyConstant.status){
                                    requireActivity().showToast(response.message)
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        dialogBinding.btnApply.text = "Apply"+"("+ 0 + ")"
                        authViewModel.searchCourseFilterListResponse.removeObservers(this)
                        if (authViewModel.searchCourseFilterListResponse.hasObservers()) return@observe
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


    private fun getCoachFilter(dialogBinding: FilterDialogBinding) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.coachFilterEntityApiCall()
            authViewModel.coachFilterEntityResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.coachFilterEntityResponse.removeObservers(this)
                        if (authViewModel.coachFilterEntityResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("filterData", response.toString())
                                if (response.status == MyConstant.status){
                                    val mt_workoutType = response.data.workouttypes
                                    val tagList: ArrayList<String> = ArrayList(mt_workoutType.map { it.workoutType })
                                    dialogBinding.TagWorkoutMtType.tags = tagList
                                    val mt_experience = response.data.experiences
                                    val tagList_experience: ArrayList<String> = ArrayList(mt_experience.map { it.experience })
                                    dialogBinding.TagExperience.tags = tagList_experience
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.coachFilterEntityResponse.removeObservers(this)
                        if (authViewModel.coachFilterEntityResponse.hasObservers()) return@observe
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

    private fun getCourseFilter(dialogBinding: FilterDialogBinding) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            showLoader()
            authViewModel.courseFilterEntityApiCall()
            authViewModel.courseFilterEntityResponse.observe(this){
                when(it){
                    is Resource.Success->{
                        hideLoader()
                        authViewModel.courseFilterEntityResponse.removeObservers(this)
                        if (authViewModel.courseFilterEntityResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                Log.d("filterData", response.toString())
                                if (response.status == MyConstant.status){
                                    setWorkTypeList(response.data.workout_types, dialogBinding)
                                    setLevelList(response.data.batch_levels, dialogBinding)
                                    setGoalList(response.data.batch_goals, dialogBinding)
                                }
                            }
                        }
                    }
                    is Resource.Loading-> {
                        hideLoader()
                    }
                    is Resource.Failure-> {
                        authViewModel.courseFilterEntityResponse.removeObservers(this)
                        if (authViewModel.courseFilterEntityResponse.hasObservers()) return@observe
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

    private fun setWorkTypeList(workoutTypes: java.util.ArrayList<WorkoutType>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idFlowlayout.setAdapter(object : TagAdapter<WorkoutType>(workoutTypes) {
            override fun getView(parent: FlowLayout?, position: Int, t: WorkoutType?): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayout, false) as TextView
                tv.text = t!!.workout_type
                return tv
            }

            fun setSelected(position: Int, s: String): Boolean {
                return s == "Android"
            }
        })

        dialogBinding.idFlowlayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            typeId = workoutTypes[position].id
            requireActivity().showToast(workoutTypes[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayout.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })

        /*//sigle check
        dialogBinding.idFlowlayout.setAdapter(object : TagAdapter<WorkoutType>(workoutTypes) {
            override fun getView(parent: FlowLayout?, position: Int, s: WorkoutType): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayout, false) as TextView
                tv.text = workoutTypes[position].workout_type
                return tv
            }
        })

        dialogBinding.idFlowlayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            requireActivity().showToast(workoutTypes[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayout.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })*/
    }

    private fun setLevelList(batchLevel: ArrayList<BatchLevel>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idFlowlayoutLevel.setAdapter(object : LevelTagAdapter<BatchLevel>(batchLevel) {
            override fun getView(parent: FlowLayout?, position: Int, t: BatchLevel?): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayoutLevel, false) as TextView
                tv.text = t!!.level_name
                return tv
            }

            fun setSelected(position: Int, s: String): Boolean {
                return s == "Android"
            }
        })

        dialogBinding.idFlowlayoutLevel.setOnTagClickListener(LevelTagFlowLayout.OnTagClickListener { view, position, parent ->
            levelId = batchLevel[position].id
            requireActivity().showToast(batchLevel[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayoutLevel.setOnSelectListener(LevelTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })

      /*  //sigle check
        dialogBinding.idFlowlayout.setAdapter(object : TagAdapter<WorkoutType>(workoutTypes) {
            override fun getView(parent: FlowLayout?, position: Int, s: WorkoutType): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayout, false) as TextView
                tv.text = workoutTypes[position].workout_type
                return tv
            }
        })

        dialogBinding.idFlowlayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            requireActivity().showToast(workoutTypes[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayout.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })*/
    }

    private fun setGoalList(batchGoal: ArrayList<BatchGoal>, dialogBinding: FilterDialogBinding) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idFlowlayoutGole.setAdapter(object : GoalTagAdapter<BatchGoal>(batchGoal) {
            override fun getView(parent: FlowLayout?, position: Int, t: BatchGoal?): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayoutGole, false) as TextView
                tv.text = t!!.goal_name
                return tv
            }

            fun setSelected(position: Int, s: String): Boolean {
                return s == "Android"
            }
        })

        dialogBinding.idFlowlayoutGole.setOnTagClickListener(GoalTagFlowLayout.OnTagClickListener { view, position, parent ->
            goalId = batchGoal[position].id
            requireActivity().showToast(batchGoal[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayoutGole.setOnSelectListener(GoalTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })

       /* //sigle check
        dialogBinding.idFlowlayout.setAdapter(object : TagAdapter<WorkoutType>(workoutTypes) {
            override fun getView(parent: FlowLayout?, position: Int, s: WorkoutType): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idFlowlayout, false) as TextView
                tv.text = workoutTypes[position].workout_type
                return tv
            }
        })

        dialogBinding.idFlowlayout.setOnTagClickListener(TagFlowLayout.OnTagClickListener { view, position, parent ->
            requireActivity().showToast(workoutTypes[position].id.toString())
            //view.setVisibility(View.GONE);
            true
        })

        dialogBinding.idFlowlayout.setOnSelectListener(TagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })*/
    }



    private fun setAllBatchesAdapter(courseList: ArrayList<ListData>) {
        binding.recyclerAllBatch.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerAllBatch.adapter = AllBatchesAdapter(context, courseList, object :
            CourseListItemPosition<Int> {
            override fun onCourseListItemPosition(item: ListData, position: Int) {
                val course_id = item.courseId
                activity!!.startActivity(Intent(requireContext(), CourseDetailActivity::class.java).putExtra("course_id", course_id.toString()))
            }
        })
    }

    private fun setMotivatorListAdapter(coachList: List<Data>) {
        binding.recyclerAllBatch.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = MotivatorListAdapter(requireContext(), coachList,
                object : CoachListItemPosition<Int> {
                    override fun onCoachListItemPosition(item: Data, position: Int) {
                        val coach_id = item.id
                        Log.d("id", coach_id.toString())
                        Timber.tag("id").i(coach_id.toString())
                        activity!!.startActivity(Intent(context, MotivatorDetailActivity::class.java).putExtra("id", item.id.toString()))
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

    override fun onResume() {
        handleTitle(resources.getString(R.string.workout_batchs))
        super.onResume()
    }
}