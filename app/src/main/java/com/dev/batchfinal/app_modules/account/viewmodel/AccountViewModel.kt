package com.dev.batchfinal.app_modules.account.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.account.model.UpdateProfileData
import com.dev.batchfinal.app_modules.account.model.UpdateProfileModel
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_utils.LogUtil
import com.dev.batchfinal.app_utils.RequestHeadersUtility
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountViewModel constructor(private val repository: AccountRepository) : ViewModel(){

    val getLoginDetails = MutableLiveData<SignInModel>()
    val getUpdatedProfile = MutableLiveData<UpdateProfileModel>()
    val errorMessage = MutableLiveData<String>()
    fun requestLogin(username: String, password: String,deviceId: String) {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        //put something inside the map, could be null
        jsonParams["email"] = username
        jsonParams["password"] = password
        jsonParams["device_token"] = deviceId
        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestLogin(requestBody)
        res.enqueue(object : Callback<SignInModel> {
            override fun onResponse(
                call: Call<SignInModel>?,
                response: Response<SignInModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        // val mResObject: ResLoginModel = Gson().fromJson<Any?>(response.body().toString(), ResLoginModel::class.java) as ResLoginModel
                        getLoginDetails.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<SignInModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }

    fun requestUpdateProfile(mobile: String, name: String,dob: String, gender:String,authToken:String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        //put something inside the map, could be null
        jsonParams["mobile"] = mobile
        jsonParams["name"] = name
        jsonParams["dob"] = dob
        jsonParams["gender"] = gender
        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestUpdateProfile(requestHeaders,requestBody)
        res.enqueue(object : Callback<UpdateProfileModel> {
            override fun onResponse(
                call: Call<UpdateProfileModel>?,
                response: Response<UpdateProfileModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getUpdatedProfile.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }
            override fun onFailure(call: Call<UpdateProfileModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }






}