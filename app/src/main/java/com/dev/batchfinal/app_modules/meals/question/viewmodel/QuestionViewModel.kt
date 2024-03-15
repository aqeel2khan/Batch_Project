package com.dev.batchfinal.app_modules.question.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.account.model.UpdateProfileData
import com.dev.batchfinal.app_modules.account.model.UpdateProfileModel
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_modules.question.model.meal_goals.MealGoalsResponse
import com.dev.batchfinal.app_modules.question.repository.QuestionRepository
import com.dev.batchfinal.app_utils.LogUtil
import com.dev.batchfinal.app_utils.RequestHeadersUtility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionViewModel constructor(private val repository: QuestionRepository) : ViewModel(){
    val getMealGoalsList = MutableLiveData<MealGoalsResponse>()
    val errorMessage = MutableLiveData<String>()
    fun requestMealGoals() {
        val requestBody = JSONObject().toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestMealGoals(requestBody)
        res.enqueue(object : Callback<MealGoalsResponse> {
            override fun onResponse(
                call: Call<MealGoalsResponse>?,
                response: Response<MealGoalsResponse>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        // val mResObject: ResLoginModel = Gson().fromJson<Any?>(response.body().toString(), ResLoginModel::class.java) as ResLoginModel
                        getMealGoalsList.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<MealGoalsResponse>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }
}