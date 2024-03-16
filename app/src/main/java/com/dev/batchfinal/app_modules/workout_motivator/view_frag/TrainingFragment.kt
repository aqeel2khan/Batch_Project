package com.dev.batchfinal.app_modules.workout_motivator.view_frag

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.AllBatchesAdapter
import com.dev.batchfinal.adapter.MotivatorListAdapter
import com.dev.batchfinal.databinding.FilterDialogBinding
import com.dev.batchfinal.databinding.FragmentTrainingBinding
import com.dev.batchfinal.`interface`.CoachListItemPosition
import com.dev.batchfinal.`interface`.CourseListItemPosition
import com.dev.batchfinal.model.coach_filter_list.Experience
import com.dev.batchfinal.model.coach_filter_list.Workouttype
import com.dev.batchfinal.model.coach_list_model.Data
import com.dev.batchfinal.model.course_filter_model.BatchGoal
import com.dev.batchfinal.model.course_filter_model.BatchLevel
import com.dev.batchfinal.model.course_filter_model.WorkoutType
import com.dev.batchfinal.model.course_model.ListData
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.app_utils.CheckNetworkConnection
import com.dev.batchfinal.app_utils.MyConstant
import com.dev.batchfinal.app_utils.MyConstant.jsonObject
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.showToast
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.app_modules.workout_motivator.view.MotivatorDetailActivity
import com.dev.batchfinal.app_modules.shopping.view.CourseDetailActivity
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.JsonObject
import com.zhy.view.flowlayout.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.dev.batchfinal.out.Resource
import timber.log.Timber

@AndroidEntryPoint
class TrainingFragment : BaseFragment<FragmentTrainingBinding>() {
    private val viewModel: AllViewModel by viewModels()
    private val authViewModel by viewModels<AuthViewModel>()
    lateinit var dialogBinding: FilterDialogBinding
    var courseList: ArrayList<ListData> = ArrayList()
    var typeId: Int = 0
    var goalId: Int = 0
    var levelId: Int = 0
    var cource_workout_Id: Int = 0

    var motivator_exp_Id: Int = 0
    var motivator_type_Id: Int = 0

    private var allBatchesAdapter: AllBatchesAdapter? = null

    override fun getViewModel(): BaseViewModel {
        return  viewModel
    }

    override fun initUi() {
        buttonClicks()
        getCourseListApi("")
        print(sharedPreferences.token)
    }

    override fun getViewBinding() = FragmentTrainingBinding.inflate(layoutInflater)

    private fun buttonClicks() {
        binding.llBatch.setOnClickListener {
            binding.llBatch.background = resources.getDrawable(R.drawable.tab_sele_bg)
            binding.llMotivator.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            binding.tvBatch.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.tvMotivator.setTextColor(ContextCompat.getColor(requireContext(),R.color.welcome_txt_gry))
            binding.rlMotivatorSearch.visibility = View.GONE
            binding.recyclerAllBatch.visibility = View.VISIBLE
            binding.recyclerAllMotivator.visibility = View.GONE
            getCourseListApi("")
            binding.rlWorkoutFilter.visibility = View.VISIBLE
        }
        binding.llMotivator.setOnClickListener {
            binding.llBatch.background = resources.getDrawable(R.drawable.tab_un_sel_bg)
            binding.llMotivator.background = resources.getDrawable(R.drawable.tab_sele_bg)
            binding.tvBatch.setTextColor(ContextCompat.getColor(requireContext(),R.color.welcome_txt_gry))
            binding.tvMotivator.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
            binding.rlMotivatorSearch.visibility = View.VISIBLE
            binding.rlWorkoutFilter.visibility = View.GONE
            binding.recyclerAllBatch.visibility = View.GONE
            binding.recyclerAllMotivator.visibility = View.VISIBLE
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
    private fun getCourseListApi(searchQuery: String) {
        courseList.clear()
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            when (searchQuery) {
                "" -> {
                    showLoader()
                    authViewModel.courseListApiCall()
                }
                "course_filter" -> {
                    showLoader()

                    jsonObject.addProperty("course_level",levelId)
                    jsonObject.addProperty("workout_type_id",cource_workout_Id)
                    jsonObject.addProperty("goal_id",goalId)
                   // authViewModel.searchCoachListApiCall(jsonObject)
                    //authViewModel.searchCourseFilterListApiCall(jsonObject)
                    authViewModel.filterCourseListApiCall(jsonObject)

                }
                else -> {
                    hideLoader()
                    jsonObject.addProperty("keyword",binding.editQuery.text.trim().toString())
                   // authViewModel.searchCoachListApiCall(jsonObject)
                    //authViewModel.searchCourseFilterListApiCall(jsonObject)
                    authViewModel.filterCourseListApiCall(jsonObject)

                }
            }


           // showLoader()
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
    //                                    courseList = response.data.list
                                        Log.d("list", courseList.toString())

                                        if(response.data.list.size>0)
                                        {
                                            setAllBatchesAdapter(response.data.list)

                                        }else{
                                            Toast.makeText(requireContext(),"No workout batch found",Toast.LENGTH_SHORT).show()
                                        }
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
    //  Motivator Api with filter
    private fun getCoachListApi(searchQuery: String) {
        if (CheckNetworkConnection.isConnection(requireContext(),binding.root, true)) {
            when (searchQuery) {
                "" -> {
                    showLoader()
                    authViewModel.coachListApiCall()
                }
                "coach_filter" -> {
                    showLoader()
                    jsonObject.addProperty("experience",motivator_exp_Id)
                    jsonObject.addProperty("workout_type",motivator_type_Id)
                    authViewModel.searchCoachListApiCall(jsonObject)
                }
                else -> {
                    hideLoader()
                    jsonObject.addProperty("keyword",binding.editQuery.text.trim().toString())
                    authViewModel.searchCoachListApiCall(jsonObject)
                }
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

                    else -> {}
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    /* second  */
    private fun showFilterDialog(filterType: String) {
        dialogBinding = FilterDialogBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(dialogBinding.root)
        if (filterType.equals("workoutFilter")){
            getCourseFilter(dialogBinding)
            dialogBinding.llWorkoutFilter.visibility = View.VISIBLE
            dialogBinding.cardExpr.visibility = View.GONE
            dialogBinding.newCard.visibility = View.GONE
            val count = typeId + goalId + levelId
            dialogBinding.btnApply.text = "Apply"
            dialogBinding.btnApply.setOnClickListener {
                //code for save week price
//                dialogBinding.btnApply.text = "Apply()"
                //course filter by list
               /* jsonObject.addProperty("course_level",levelId)
                jsonObject.addProperty("workout_type_id",cource_workout_Id)
                jsonObject.addProperty("goal_id",goalId)*/
               // searchCourseListByFilterApi(jsonObject, dialogBinding)
               // searchCourseListByFilterApi(jsonObject, dialogBinding)
                getCourseListApi("course_filter")
                dialog.dismiss()
            }
            dialogBinding.onClickDismiss.setOnClickListener {
                dialog.dismiss()
            }
        }else{
            getCoachFilter(dialogBinding)
            dialogBinding.llMotivatorFilter.visibility = View.VISIBLE
            dialogBinding.btnApply.setOnClickListener {
                //code for save week price
                getCoachListApi("coach_filter")
                dialog.dismiss()
            }
            dialogBinding.onClickDismiss.setOnClickListener {
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

                    else -> {}
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
                                    setMotivatorWtFilter(response.data.workouttypes)
                                   setMotivatorExperience(response.data.experiences)
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

                    else -> {}
                }
            }
        }else{
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }

    private fun setMotivatorExperience(experiences: List<Experience>) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idCoachExperience.setAdapter(object : ExperienceTagAdapter<Experience>(experiences) {
            override fun getView(parent: FlowLayout?, position: Int, t: Experience?): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idCoachWt, false) as TextView
                tv.text = t!!.experience
                return tv
            }
        })

        dialogBinding.idCoachExperience.setOnTagClickListener(ExperienceTagFlowLayout.OnTagClickListener { view, position, parent ->
            motivator_exp_Id = experiences[position].id
            //requireActivity().showToast(experiences[position].id.toString())
            true
        })

        dialogBinding.idCoachExperience.setOnSelectListener(ExperienceTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })
    }

    private fun setMotivatorWtFilter(workoutType: List<Workouttype>) {
        val mInflater = LayoutInflater.from(activity)
        //multiple check
        dialogBinding.idCoachWt.setAdapter(object : CoachWtTagAdapter<Workouttype>(workoutType) {
            override fun getView(parent: FlowLayout?, position: Int, t: Workouttype?): View {
                val tv = mInflater.inflate(R.layout.tv, dialogBinding.idCoachWt, false) as TextView
                tv.text = t!!.workoutType
                return tv
            }
        })

        dialogBinding.idCoachWt.setOnTagClickListener(CoachWtTagFlowLayout.OnTagClickListener { view, position, parent ->
            motivator_type_Id = workoutType[position].id
           // requireActivity().showToast(workoutType[position].id.toString())
            true
        })

        dialogBinding.idCoachWt.setOnSelectListener(CoachWtTagFlowLayout.OnSelectListener { selectPosSet ->
            activity!!.title = "choose:$selectPosSet"
        })
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

                    else -> {}
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
            cource_workout_Id = workoutTypes[position].id
            //requireActivity().showToast(workoutTypes[position].id.toString())

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
           // requireActivity().showToast(batchLevel[position].id.toString())
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
            //requireActivity().showToast(batchGoal[position].id.toString())
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
       var allBatchesAdapter = AllBatchesAdapter(context, courseList,"training_screen", object :
            CourseListItemPosition<Int> {
            override fun onCourseListItemPosition(item: ListData, position: Int) {
               try {
                   val course_id = item.courseId.toString()
                   if (!course_id.isNullOrEmpty())
                   {
                       Log.e("COURSE_ID",course_id.toString())
                       requireContext().startActivity(Intent(requireContext(), CourseDetailActivity::class.java).putExtra("course_id", course_id.toString()))
                       //Replaced by BBh- activity by context
                   }else
                   {
                       Toast.makeText(requireContext(),"Batch ID not found",Toast.LENGTH_SHORT).show()

                   }

               }catch (e:Exception){e.printStackTrace()}

            }
        })
        binding.recyclerAllBatch.adapter = allBatchesAdapter
    }

    private fun setMotivatorListAdapter(coachList: List<Data>) {
        binding.recyclerAllMotivator.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            adapter = MotivatorListAdapter(requireContext(), coachList,"motivator_training",
                object : CoachListItemPosition<Int> {
                    override fun onCoachListItemPosition(item: Data, position: Int) {
                        val coach_id = item.id
                        if (coach_id.toString().isNotEmpty())
                        {
                            Log.e("id", coach_id.toString())
                            Timber.tag("id").i(coach_id.toString())
                            requireContext()!!.startActivity(Intent(context, MotivatorDetailActivity::class.java).putExtra("id", item.id.toString()))

                        }else{
                            Toast.makeText(requireContext(),"Motivator details is not available",Toast.LENGTH_SHORT).show()
                        }
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