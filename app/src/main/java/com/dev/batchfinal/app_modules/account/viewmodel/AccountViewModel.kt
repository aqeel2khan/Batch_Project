package com.dev.batchfinal.app_modules.account.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.batchfinal.app_modules.account.model.DeliveryAddressModel
import com.dev.batchfinal.app_modules.account.model.GetDelivaryAddressModel
import com.dev.batchfinal.app_modules.account.model.SignInModel
import com.dev.batchfinal.app_modules.account.model.SignUpError
import com.dev.batchfinal.app_modules.account.model.SignUpModel
import com.dev.batchfinal.app_modules.account.model.UpdateProfileModel
import com.dev.batchfinal.app_modules.account.repository.AccountRepository
import com.dev.batchfinal.app_utils.LogUtil
import com.dev.batchfinal.app_utils.MyCustom
import com.dev.batchfinal.app_utils.RequestHeadersUtility
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccountViewModel constructor(private val repository: AccountRepository) : ViewModel(){

    val getLoginDetails = MutableLiveData<SignInModel>()
    val getSignUpResponse= MutableLiveData<SignUpModel>()
    val getUpdatedProfile = MutableLiveData<UpdateProfileModel>()
    val getAddDelivaryAddress=MutableLiveData<DeliveryAddressModel>()
    val getGetDelivaryAddress=MutableLiveData<GetDelivaryAddressModel>()


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

    fun requestSignUp( email: String, password: String, mobile: String, username: String) {
        /*val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["email"] = email
        jsonParams["password"] = password
        jsonParams["mobile"] = mobile
        jsonParams["name"] = username

        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())*/

        val params = HashMap<String, String>()
        params["email"] = email
        params["password"] = password
        params["mobile"] = mobile
        params["name"] = username

        val res = repository.requestSignUp(params)
        res.enqueue(object : Callback<SignUpModel> {
            override fun onResponse(
                call: Call<SignUpModel>?,
                response: Response<SignUpModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getSignUpResponse.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message.toString())
                    }
                }else {

                    Log.e("RES_BODY",response.errorBody().toString())
                    val errorResponse: SignUpError = Gson().fromJson(response.errorBody()!!.string(), SignUpError::class.java)
                    errorMessage.postValue(errorResponse.message.toString()+"-"+errorResponse.errors!!)

                }
            }

            override fun onFailure(call: Call<SignUpModel>?, t: Throwable?) {
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


    fun requestAddDelivaryAddress(authToken: String, mCountry: String,mState: String,mCity: String
                                  ,mAddress1: String,mAddress2: String,mPostalCode: String,mType: String,isDefault: String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["country"] = mCountry
        jsonParams["state"] = mState
        jsonParams["city"] = mCity
        jsonParams["address_line_1"] = mAddress1
        jsonParams["address_line_2"] = mAddress2
        jsonParams["postal_code"] = mPostalCode
        jsonParams["type"] = mType
        jsonParams["is_default"] = isDefault




        val requestBody = JSONObject(jsonParams).toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        val res = repository.requestAddDelivaryAddress(requestHeaders,requestBody)
        res.enqueue(object : Callback<DeliveryAddressModel> {
            override fun onResponse(
                call: Call<DeliveryAddressModel>?,
                response: Response<DeliveryAddressModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getAddDelivaryAddress.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<DeliveryAddressModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }

    fun getGetDelivaryAddress(authToken: String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)
        val res = repository.requestGetDelivaryAddress(requestHeaders)
        res.enqueue(object : Callback<GetDelivaryAddressModel> {
            override fun onResponse(
                call: Call<GetDelivaryAddressModel>?,
                response: Response<GetDelivaryAddressModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getGetDelivaryAddress.postValue(response.body())
                    }else
                    {
                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }

            override fun onFailure(call: Call<GetDelivaryAddressModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }



}