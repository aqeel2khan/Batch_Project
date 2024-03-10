package com.dev.batchfinal.app_modules.scanning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.scanning.model.CourseOrderListModel
import com.dev.batchfinal.app_modules.scanning.model.MealSubscriptionDetailsResponse
import com.dev.batchfinal.app_modules.scanning.repository.ScanningRepository
import com.dev.batchfinal.app_utils.LogUtil
import com.dev.batchfinal.app_utils.RequestHeadersUtility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScanningViewModel constructor(private val repository: ScanningRepository) : ViewModel() {

    val getMealSubscriptionResponse = MutableLiveData<MealSubscriptionDetailsResponse>()
    val getCourseOrderResponse = MutableLiveData<CourseOrderListModel>()

    val errorMessage = MutableLiveData<String>()

    fun requestMealSubscription(userId: String, authToken:String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["user_id"] = userId
        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestMealSubsCription(requestHeaders,requestBody)
        res.enqueue(object : Callback<MealSubscriptionDetailsResponse> {
            override fun onResponse(
                call: Call<MealSubscriptionDetailsResponse>?,
                response: Response<MealSubscriptionDetailsResponse>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getMealSubscriptionResponse.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<MealSubscriptionDetailsResponse>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }


    fun requestCourseOrder(userId: String, authToken:String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["user_id"] = userId
        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestCourseOrder(requestHeaders,requestBody)
        res.enqueue(object : Callback<CourseOrderListModel> {
            override fun onResponse(
                call: Call<CourseOrderListModel>?,
                response: Response<CourseOrderListModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getCourseOrderResponse.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<CourseOrderListModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }

}