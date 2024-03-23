package com.dev.batchfinal.app_modules.scanning.view_frag


//import com.google.android.gms.auth.api.signin.GoogleSignIn
//import com.google.android.gms.auth.api.signin.GoogleSignInAccount
//import com.google.android.gms.auth.api.signin.GoogleSignInOptions
//import com.google.android.gms.common.api.ApiException
//import com.google.android.gms.fitness.Fitness
//import com.google.android.gms.fitness.data.DataType
//import com.google.android.gms.fitness.data.Field
//import com.google.android.gms.fitness.request.DataReadRequest
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.dev.batchfinal.R
import com.dev.batchfinal.adapter.CourseOrderAdapter
import com.dev.batchfinal.adapter.MealSubscribeListAdapter
import com.dev.batchfinal.app_common.BaseFragment
import com.dev.batchfinal.app_modules.account.view.LoginActivity
import com.dev.batchfinal.app_modules.activity.WeightLossActivity
import com.dev.batchfinal.app_modules.meals.meal_purchase.view.activity.CurrentMealDetailActivity
import com.dev.batchfinal.app_modules.scanning.work_manager.VimeoVideoWorker
import com.dev.batchfinal.app_session.UserSessionManager
import com.dev.batchfinal.app_utils.*
import com.dev.batchfinal.databinding.FragmentScaningBinding
import com.dev.batchfinal.databinding.HomeMealDialogBinding
import com.dev.batchfinal.databinding.ProfileEditDialogBinding
import com.dev.batchfinal.`interface`.CourseOrderListItemPosition
import com.dev.batchfinal.`interface`.MealSubscribeListPosition
import com.dev.batchfinal.model.courseorderlist.Data
import com.dev.batchfinal.model.courseorderlist.OrderList
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListRequest
import com.dev.batchfinal.model.subscribe_list_model.MealSubscribeListResponse
import com.dev.batchfinal.out.AuthViewModel
import com.dev.batchfinal.out.Resource
import com.dev.batchfinal.viewmodel.AllViewModel
import com.dev.batchfinal.viewmodel.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.text.DecimalFormat
import kotlin.math.roundToInt

@AndroidEntryPoint
class ScanningFragment : BaseFragment<FragmentScaningBinding>() {
    private val RC_SIGN_IN=200
    private lateinit var courseData: Data
    lateinit var dialogOptionBinding: ProfileEditDialogBinding
    private lateinit var sessionManager: UserSessionManager


    private val viewModel: AllViewModel by viewModels()

    private val authViewModel by viewModels<AuthViewModel>()

    lateinit var dialogBinding: HomeMealDialogBinding

    var courseList: ArrayList<OrderList> = ArrayList()
    var meal_id:String=""
    var subscribe_id:String=""
    var calories:String=""
    var fat:Double=0.0
    var carbs:Double=0.0
    var protein:Double=0.0
    override fun getViewModel(): BaseViewModel {
        return viewModel
    }


    override fun initUi() {
        sessionManager= UserSessionManager(requireContext())
        if (sessionManager.isloggin())
        {
            getMealSubscribeListApi()
            getCourseOrderListApi()
            //setAllCourseOrderAdapter(courseList)
        }else
        {
           askUserForLogin("Required authorization to access scanning batch.")
        }
        buttonClicks()

    }

    override fun onStart() {

        super.onStart()
        /*val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val endTime = calendar.timeInMillis

        calendar.add(Calendar.DAY_OF_MONTH, -1)
        val startTime = calendar.timeInMillis

        val readRequest = DataReadRequest.Builder()
            .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
            .bucketByTime(1, TimeUnit.DAYS) // Specify the bucketing strategy (1 day in this example)
            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
            .build()*/

       /* val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken("756744687216-0trk334visimpv57ifig6bhg4bdkh8pu.apps.googleusercontent.com")
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        val account = GoogleSignIn.getLastSignedInAccount(requireContext())*/
        /* if(account!=null)
         {
             Fitness.getHistoryClient(requireContext(), GoogleSignIn.getLastSignedInAccount(requireContext()))
                 .readData(readRequest)
                 .addOnSuccessListener { dataReadResponse ->
                     val dataSet = dataReadResponse.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
                     for (dataPoint in dataSet.dataPoints) {
                         // Access data from the data point
                         val timestamp = dataPoint.getTimestamp(TimeUnit.MILLISECONDS)
                         val stepCount = dataPoint.getValue(Field.FIELD_STEPS).asInt()
                         Log.e("STEPCOUT",stepCount.toString())
                         Log.e("TIMESTAMP",timestamp.toString())

                     }
                 }
                 .addOnFailureListener { e ->
                     Log.e("", "There was an error reading data from Google Fit", e)
                 }

         }else
         {
             Log.e("", "account is null")
             val signInIntent = googleSignInClient.signInIntent
             startActivityForResult(signInIntent, RC_SIGN_IN)

         }
 */

    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//        GlobalScope.launch(Dispatchers.Main) {
//            try {
//                val account = task.getResult(ApiException::class.java)
//                val historyClient = Fitness.getHistoryClient(requireContext(), account)
//                val calendar = Calendar.getInstance()
//                calendar.add(Calendar.DAY_OF_MONTH, -1)
//                val endTime = calendar.timeInMillis
//                calendar.add(Calendar.DAY_OF_MONTH, -1)
//                val startTime = calendar.timeInMillis
//
//                val readRequest = DataReadRequest.Builder()
//                    .aggregate(DataType.TYPE_STEP_COUNT_DELTA, DataType.AGGREGATE_STEP_COUNT_DELTA)
//                    .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
//                    .build()
//
//                historyClient.readData(readRequest)
//                    .addOnSuccessListener { dataReadResponse ->
//                        val dataSet = dataReadResponse.getDataSet(DataType.TYPE_STEP_COUNT_DELTA)
//                        for (dataPoint in dataSet.dataPoints) {
//                            // Access data from the data point
//                            val timestamp = dataPoint.getTimestamp(TimeUnit.MILLISECONDS)
//                            val stepCount = dataPoint.getValue(Field.FIELD_STEPS).asInt()
//                            Log.e("TAG-STEPCOUT", stepCount.toString())
//                            Log.e("TAG-TIMESTAMP", timestamp.toString())
//
//                        }
//                    }
//                    .addOnFailureListener { e ->
//                        Log.e("TAG", "There was an error reading data from Google Fit", e)
//                    }
//            } catch (e: ApiException) {
//                // Handle sign-in failure
//                e.statusCode
//                Log.e("API_EXCEPTION",""+e.statusCode)
//            }
//
//
//        }
//    }





    private fun buttonClicks() {
       /* binding.cdCurrentMeal.setOnClickListener {
            findNavController().navigate(
                R.id.action_scanFragment_to_mealBatchFragment,
                MealBatchFragment.getBundle("","")
            )
        }*/
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
            mealSubscribeListRequest.userId=sessionManager.getUserId()
            authViewModel.mealSubscribeListApiCall(mealSubscribeListRequest)
            Log.d("Token","Bearer " + sessionManager.getTokenID());
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
                                    if (response.data.internalData.size>0)
                                    {
                                        meal_id=response.data.internalData.first().id.toString()
                                        subscribe_id=response.data.internalData.first().subscribedId.toString()
                                        getMecro(meal_id,subscribe_id)

                                        setAllMealSubscribeListAdapter(response.data.internalData)
                                    }else{
                                        Toast.makeText(requireContext(),"Meal subscriber list is empty",Toast.LENGTH_SHORT).show()
                                    }

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
           // showLoader()
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
                               // Log.e("RES_SCANNING",response.data.toString())
                                // Log.d("response_order",response.data.toString())
                                if (response.status == MyConstant.success) {
                                    courseList = response.data.list

                                    // NOTE - NEW WAY IMPLEMENTATION OF VIMEO-IN PROGRESS
                                    /*val videoKey= courseList[1].course_detail.course_duration[0].course_duration_exercise[0].video_id
                                      Log.e("VIDEO_KEY",videoKey)
                                     val inputData = workDataOf("videoKey" to "911682062")
                                     val workRequest = OneTimeWorkRequest.Builder(VimeoVideoWorker::class.java)
                                        .setInputData(inputData)
                                        .build()
                                    // Enqueue the WorkRequest
                                    WorkManager.getInstance(requireContext()).enqueue(workRequest)*/

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
                        hideLoader()
                        authViewModel.courseOrderListResponse.removeObservers(this)
                        if (authViewModel.courseOrderListResponse.hasObservers()) return@observe
                        Log.e("RES_SCANNING",it.errorBody.toString())
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")

                    }
                }
            }
        } else {
            hideLoader()

            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
    }



    private fun setAllMealSubscribeListAdapter(internalDatum: List<MealSubscribeListResponse.InternalDatum>) {
        binding.recyclerMealSubscribe.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerMealSubscribe.adapter = MealSubscribeListAdapter(context, internalDatum, object :
            MealSubscribeListPosition<Int> {
            override fun onMealSubscribeListItemPosition(item: MealSubscribeListResponse.InternalDatum, position: Int) {
                try {
                    var mIntent= Intent(requireContext(), CurrentMealDetailActivity::class.java)

                    if(item!=null && item.id!=null){
                        mIntent .putExtra("meal_id",item.id.toString())
                    }else{
                        mIntent .putExtra("meal_id","")
                    }
                    if(item!=null && item.subscribedId!=null){
                        mIntent .putExtra("subscribe_id",item.subscribedId.toString())
                    }else{
                        mIntent .putExtra("subscribe_id","")
                    }
                    if(item!=null && item.goalId!=null){
                        mIntent .putExtra("goal_id",item.goalId.toString())
                    }else{
                        mIntent .putExtra("goal_id","")
                    }

                    requireContext().startActivity(mIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        })
    }
    private fun setAllCourseOrderAdapter(courseList: ArrayList<OrderList>) {
        binding.recyclerCourseOrder.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerCourseOrder.adapter = CourseOrderAdapter(context, courseList, object :
            CourseOrderListItemPosition<Int> {
            override fun onCourseOrderListItemPosition(item: OrderList, position: Int) {
                try {
                    val gson = Gson()
                    val mIntent= Intent(requireContext(), WeightLossActivity::class.java)
                    mIntent .putExtra("order_list", gson.toJson(item))
                    requireContext().startActivity(mIntent)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }



    private fun getMecro(meal_id:String,subscribe_id:String) {
        if (CheckNetworkConnection.isConnection(requireContext(), binding.root, true)) {
            showLoader()
            val jsonObject = JsonObject()
            jsonObject.addProperty("user_id",sessionManager.getUserId())
            jsonObject.addProperty("subscribed_id",subscribe_id)
            jsonObject.addProperty("meal_id",meal_id)

            authViewModel.getMecrosApiCall(jsonObject)
            authViewModel.mecrosResponse.observe(this) {
                when (it) {
                    is Resource.Success -> {
                        hideLoader()
                        authViewModel.mecrosResponse.removeObservers(this)
                        if (authViewModel.mecrosResponse.hasObservers()) return@observe
                        lifecycleScope.launch {
                            it.let {
                                val response = it.value
                                // showToast(response.message)

                                if (response.status == MyConstant.success) {
                                    calories=response.data.internaldata.calories
                                     fat=response.data.internaldata.fat
                                     carbs=response.data.internaldata.carbs
                                     protein=response.data.internaldata.protein
                                     val per_fat: Double = fat / 100.0f * 10
                                     val per_carbs: Double = carbs / 100.0f * 10
                                     val per_protein: Double = protein / 100.0f * 10


                                    binding.txtCal.text=calories
                                    binding.txtFat.text=fat.roundToInt().toString()+"% Fat"
                                    binding.txtCarbs.text=carbs.roundToInt().toString()+"% Carbs"
                                    binding.txtProteins.text=protein.roundToInt().toString()+"% Protein"
                                    binding.seekbarFat.progress=fat.roundToInt()
                                    binding.seekbarCarb.progress=carbs.roundToInt()
                                    binding.seekbarProtein.progress=protein.roundToInt()
                                }
                            }
                        }
                    }

                    is Resource.Loading -> {
                        hideLoader()
                    }

                    is Resource.Failure -> {
                        authViewModel.mecrosResponse.removeObservers(this)
                        if (authViewModel.mecrosResponse.hasObservers()) return@observe
                        hideLoader()
                        MyCustom.errorBody(binding.root.context, it.errorBody, "")
                    }
                }
            }
        } else {
            binding.root.context.showToast(binding.root.context.getString(R.string.internet_is_not_available))
        }
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