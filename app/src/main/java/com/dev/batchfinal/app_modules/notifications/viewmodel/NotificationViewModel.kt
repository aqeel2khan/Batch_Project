package com.dev.batchfinal.app_modules.notifications.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dev.batchfinal.app_modules.notifications.model.NotificationsModel
import com.dev.batchfinal.app_modules.notifications.repository.NotificationRepository
import com.dev.batchfinal.app_utils.LogUtil
import com.dev.batchfinal.app_utils.RequestHeadersUtility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationViewModel constructor(private val repository: NotificationRepository) : ViewModel() {


    val getNotifications = MutableLiveData<NotificationsModel>()
    val errorMessage = MutableLiveData<String>()

    fun requestNotifications( authToken:String) {
        val requestHeaders = RequestHeadersUtility.requestHeaderAuthorization(authToken)
        val res = repository.requestNotification(requestHeaders)
        res.enqueue(object : Callback<NotificationsModel> {
            override fun onResponse(
                call: Call<NotificationsModel>?,
                response: Response<NotificationsModel>?
            ) {
                if (response!!.isSuccessful) {
                    LogUtil.showLog("LOGIN RES", response!!.body().toString())

                    if (response.body()?.status!!)
                    {
                        getNotifications.postValue(response.body())
                    }else
                    {

                        errorMessage.postValue(response.body()!!.message+" ERROR CODE-" + response.code())
                    }
                } else {

                    errorMessage.postValue("Some error occurred, ERROR CODE-" + response.code())
                }
            }
            override fun onFailure(call: Call<NotificationsModel>?, t: Throwable?) {
                errorMessage.postValue(t?.message)
            }
        })
    }



}